package com.lzk.service;

import com.lzk.entity.User;

import java.util.List;

public interface UserService {
    List<User> findUsers(User a);
    void addUser(User a);
    void changeUser(User a);
}
