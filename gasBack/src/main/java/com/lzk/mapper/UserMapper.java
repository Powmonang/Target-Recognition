package com.lzk.mapper;

import com.lzk.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUsers(User a);
    void addUser(User a);
    void changeUser(User a);
}
