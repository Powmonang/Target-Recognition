package com.defectscan.service;

import com.defectscan.entity.User;

import java.util.List;

public interface UserService {
    User findUsers(User a);
    void addUser(User a);
    void changeUser(User a);
}
