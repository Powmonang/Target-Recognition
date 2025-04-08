package com.defectscan.service.impl;

import com.defectscan.entity.User;
import com.defectscan.mapper.UserMapper;
import com.defectscan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findUsers(User a) {
        return userMapper.findUsers(a);
    }

    @Override
    public int findUserByUsername(String username) {
        return userMapper.findUserByUserneme(username);
    }

    @Override
    public void addUser(User a) {
        userMapper.addUser(a);
    }

    @Override
    public void changeUser(User a) {
        userMapper.changeUser(a);
    }
}
