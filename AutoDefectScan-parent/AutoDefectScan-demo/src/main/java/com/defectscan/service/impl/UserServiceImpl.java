package com.defectscan.service.impl;


import com.defectscan.mapper.UserMapper;
import com.defectscan.entity.User;
import com.defectscan.result.Result;
import com.defectscan.service.UserService;
import com.defectscan.utils.SafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SafeUtil safeUtil;


    /**
     * 通过用户名从数据库中获取用户信息
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    /**
     * 登陆用户
     * @param user
     */
    @Override
    public void login(User user) {
        user.setUpdateUser(user.getUsername());
        user.setUpdateTime(LocalDateTime.now().toString());
        userMapper.updateUser(user);
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean registerUser(User user) {
        if (userMapper.checkUsernameExists(user.getUsername()) > 0) {
            return false; // 如果用户名已存在，返回false(表示已存在该用户名,注册失败）
        } else {
            //获取原始密码
            String password = user.getPassword();
            String hashPassword = safeUtil.hashPassword(password);
            //进行加密操作 再添加
            user.setPassword(hashPassword);
            user.setCreateTime(LocalDateTime.now().toString());
            user.setUpdateTime(LocalDateTime.now().toString());
            user.setCreateUser(user.getUsername());
            user.setUpdateUser(user.getUsername());
            userMapper.addUser(user);
            return true;//注册成功返回true
        }
    }

    @Override
    public void updateUser(User user) {
        user.setCreateTime(LocalDateTime.now().toString());
        user.setUpdateTime(LocalDateTime.now().toString());
        user.setCreateUser(user.getUsername());
        user.setUpdateUser(user.getUsername());
        userMapper.updateUser(user);
    }




    @Override
    public void delete(List<Integer> ids) {
        userMapper.delete(ids);
    }

}
