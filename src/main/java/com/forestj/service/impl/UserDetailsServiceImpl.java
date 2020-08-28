package com.forestj.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forestj.mapper.RoleMapper;
import com.forestj.mapper.UserMapper;
import com.forestj.pojo.JwtUser;
import com.forestj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * @author shuang.kou
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        /**
         * 用户名或id相等都行
         */

        wrapper.eq("username",name).or().eq("email",name);
        User user = userMapper.selectOne(wrapper);
        if(user == null){
            return null;
        }
        user.setRoles(roleMapper.selectRoleByUsername(user.getUserId()));
        return new JwtUser(user);
    }

    public UserDetails loadUserByUserId(Integer userId){
        User user = userMapper.selectById(userId);
        if(user == null){
            return null;
        }
        user.setRoles(roleMapper.selectRoleByUsername(user.getUserId()));
        return new JwtUser(user);
    }

}
