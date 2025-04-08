package com.defectscan.service;

import com.defectscan.entity.User;

public interface UserService {
    User findUsers(User a);
    int findUserByUsername(String username);
    void addUser(User a);
    void changeUser(User a);
}
