package com.defectscan.service.impl;


import com.defectscan.mapper.UserMapper;
import com.defectscan.entity.User;
import com.defectscan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void delete(List<Integer> ids) {
        userMapper.delete(ids);
    }

    @Override
    public void save(User emp) {
        //已有公共字段填充
        //emp.setCreateTime(LocalDateTime.now());
        //emp.setUpdateTime(LocalDateTime.now());
        userMapper.insert(emp);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User emp) {
        //已有公共字段填充
        //emp.setUpdateTime(LocalDateTime.now());

        userMapper.update(emp);
    }

    @Override
    public User login(User emp) {
        return userMapper.getByUsernameAndPassword(emp);
    }
}
