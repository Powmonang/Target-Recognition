package com.defectscan.service.impl;

import com.defectscan.entity.User;
import com.defectscan.mapper.UserMapper;
import com.defectscan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class userA implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findUsers(User a) {
        // 把性别解析回去
        Map<String, String> sexMap = new HashMap<>();
        sexMap.put("0", "女");
        sexMap.put("1", "男");
        sexMap.put("2", "不详");
        List<User> result = userMapper.findUsers(a);
        for(User i:result){
            i.setSex(sexMap.get(i.getSex()));
        }
        return result;
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
