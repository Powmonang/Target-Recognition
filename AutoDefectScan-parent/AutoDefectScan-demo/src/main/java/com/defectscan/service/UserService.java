package com.defectscan.service;

import com.defectscan.entity.User;

import java.util.List;

/**
 * 用户管理
 */
public interface UserService {

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 确认登录(更新登陆时间）
     * @param user
     * @return
     */
    void login(User user);

    /**
     * 新增(注册）用户
     * @param emp
     */
    boolean registerUser(User emp);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);


    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);








}
