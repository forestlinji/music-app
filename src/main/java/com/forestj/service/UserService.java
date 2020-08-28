package com.forestj.service;

import com.forestj.pojo.Role;
import com.forestj.pojo.User;
import com.forestj.pojo.UserInfo;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserById(Integer id);

    void register(User user);

    void update(User user);

    UserInfo getUserInfo(Integer id);

    int getCollectsNum(Integer id);

    List<Role> getRole(Integer id);
}
