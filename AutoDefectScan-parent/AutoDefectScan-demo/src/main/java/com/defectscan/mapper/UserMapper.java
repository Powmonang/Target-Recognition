package com.defectscan.mapper;

import com.defectscan.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findUsers(User a);
    int findUserByUserneme(String userneme);
    void addUser(User a);
    void changeUser(User a);
}
