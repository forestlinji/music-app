package com.forestj.controller;

import com.forestj.pojo.*;
import com.forestj.service.CollectionService;
import com.forestj.service.MailService;
import com.forestj.service.UserService;
import com.forestj.vo.ChangePasswordVo;
import com.forestj.vo.ForgetPasswordVo;
import com.forestj.vo.RegisterVo;
import com.forestj.vo.UserInfoVo;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MailService mailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private CollectionService collectionService;

    /**
     * 查询是否重名
     * @param username
     * @return
     */
    @GetMapping("username")
    public ResponseJson DuplicateUsername(String username) {
        User user = userService.findUserByUsername(username);
        if (user != null) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        } else return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 查询是否存在email
     * @param email
     * @return
     */
    @GetMapping("email")
    public ResponseJson DuplicateEmail(String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        } else return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 发送激活码
     * @param email
     * @return
     */
    @GetMapping("activeCode")
    public ResponseJson ActiveCode(String email) {
        if (!validate(email)) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        String activeCode = RandomStringUtils.randomAlphanumeric(4);
        redisTemplate.opsForValue().set("active:" + email, activeCode, 10, TimeUnit.MINUTES);
        mailService.SendActiveMail(email, activeCode);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 注册
     * @param registerVo
     * @return
     */
    @PostMapping("signup")
    public ResponseJson Signup(@RequestBody @Valid RegisterVo registerVo) {
        String username = registerVo.getUsername();
        String password = registerVo.getPassword();
        String email = registerVo.getEmail();
        String activeCode = registerVo.getActiveCode();
        if (userService.findUserByUsername(username) != null ||
                userService.findUserByEmail(email) != null) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        if (!redisTemplate.opsForValue().get("active:" + email).equals(activeCode)) {
            return new ResponseJson(ResultCode.WRONGINFO);
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        user.setIsBan(false);
        Date current = new Date();
        user.setCreateTime(current);
        user.setUpdateTime(current);
        userService.register(user);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 找回密码token获取
     * @param email
     * @return
     */
    @GetMapping("forgetGet")
    public ResponseJson forgetGet(String email) {
        if (!validate(email)) {
            return new ResponseJson(ResultCode.UNVALIDPARAMS);
        }
        String changeToken = RandomStringUtils.randomAlphanumeric(4);
        redisTemplate.opsForValue().set("change:" + email, changeToken, 10, TimeUnit.MINUTES);
        mailService.SendForgetMail(email, changeToken);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 找回密码
     * @param forgetPasswordVo
     * @return
     */
    @PostMapping("forget")
    public ResponseJson forget(@RequestBody @Valid ForgetPasswordVo forgetPasswordVo) {
        String changeToken = forgetPasswordVo.getChangeToken();
        String password = forgetPasswordVo.getNewPassword();
        String email = forgetPasswordVo.getEmail();
        //判断token是否正确
        String s = redisTemplate.opsForValue().get("change:" + email);
        if (!changeToken.equals(s)) {
            return new ResponseJson(ResultCode.WRONGINFO);
        }
        User user = userService.findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        Date current = new Date();
        user.setUpdateTime(current);
        userService.update(user);
        redisTemplate.delete("change:" + email);
        //将当前用户存入token黑名单，使原有token失效
        redisTemplate.opsForValue().set("blacklist:" + user.getUserId(), String.valueOf(current.getTime()), 30, TimeUnit.DAYS);
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 更改密码
     * @param changePasswordVo
     * @return
     */
    @PostMapping("/changePassword")
    public ResponseJson changePassword(@Valid @RequestBody ChangePasswordVo changePasswordVo) {
        String oldPassword = changePasswordVo.getOldPassword();
        String newPassword = changePasswordVo.getNewPassword();
        JwtUser currentUser = this.currentUser.getCurrentUser();
        //解码并判断密码合法性
        try {
            if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
                return new ResponseJson(ResultCode.WRONGINFO);
            }
            Integer userId = Integer.parseInt(currentUser.getUsername());
            User user = userService.findUserById(userId);
            user.setPassword(passwordEncoder.encode(newPassword));
            Date current = new Date();
            user.setUpdateTime(current);
            userService.update(user);
            redisTemplate.opsForValue().set("blacklist:" + user.getUserId(), String.valueOf(current.getTime()), 30, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseJson(ResultCode.SUCCESS);
    }

    /**
     * 查询收藏
     * @return
     */
    @GetMapping("collects")
    public ResponseJson<List<Song>> getCollects() {
        String userId = currentUser.getCurrentUser().getUsername();
        List<Song> collections = collectionService.getCollections(Integer.parseInt(userId));
        return new ResponseJson<>(ResultCode.SUCCESS, collections);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("getUserInfo")
    public ResponseJson<UserInfo> getUserInfo(){
        String userId = currentUser.getCurrentUser().getUsername();
        UserInfo userInfo = userService.getUserInfo(Integer.parseInt(userId));
        return new ResponseJson<>(ResultCode.SUCCESS, userInfo);
    }

    /**
     * 获取角色列表
     * @return
     */
    @GetMapping("getRole")
    public ResponseJson<UserInfoVo> getRole(){
        int userId = Integer.parseInt(currentUser.getCurrentUser().getUsername());
        UserInfo userInfo = userService.getUserInfo(userId);
        List<Role> role = userService.getRole(userId);
        List<String> roleList = role.stream().map(rol -> rol.getRoleName()).collect(Collectors.toList());
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(userInfo.getUserId());
        userInfoVo.setUsername(userInfo.getUsername());
        userInfoVo.setEmail(userInfo.getEmail());
        userInfoVo.setRoles(roleList);
        return new ResponseJson<UserInfoVo>(ResultCode.SUCCESS, userInfoVo);
    }


    private static boolean validate(String email) {
        boolean isExist = false;
        //在正则表达式中\w表示任意单个字符范围是a-z,A-Z,0-9,因为在java中\本来就是转义符号，如果只写为\w则会发生歧义，甚至错误,
        //因此要写为：\\w+的意思就是出现一次以上，所以\\w+就代表任意长度的字符串，但不包括其他特殊字符 ，如_,-,$,&,*等
        //(\\w+.)+表示服务器可能有多级域名，[a-z]{2,3}表示最多有2-3个域名。
        if ((Pattern.matches("\\w+@(\\w+.)+[a-z]{2,3}", email))) {
            System.out.println("有效邮件地址");
            isExist = true;
        } else {
            System.out.println("无效邮件地址");
        }
        return isExist;
    }

    @GetMapping("test")
    public ResponseJson<Date> testt(){
        return new ResponseJson<>(ResultCode.SUCCESS,new Date());
    }


}
