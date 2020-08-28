package com.forestj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.forestj.mapper.RoleMapper;
import com.forestj.mapper.UserInfoMapper;
import com.forestj.mapper.UserMapper;
import com.forestj.pojo.Role;
import com.forestj.pojo.User;
import com.forestj.pojo.UserInfo;
import com.forestj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public User findUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional
    public void register(User user) {
        int id = userMapper.insert(user);
        System.out.println(id);
        System.out.println(user.getUserId());
        roleMapper.insertUserRole(user.getUserId());
        log.info(user.getUserId()+"注册");
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public UserInfo getUserInfo(Integer id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public int getCollectsNum(Integer id) {
        return userMapper.getCollectsNum(id);
    }

    @Override
    public List<Role> getRole(Integer id) {
        return roleMapper.selectRoleByUsername(id);
    }
}
