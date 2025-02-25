package com.defectscan.service;

import com.defectscan.entity.User;

import java.util.List;

/**
 * 用户管理
 */
public interface UserService {

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增用户
     * @param emp
     */
    void save(User emp);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 更新用户
     * @param emp
     */
    void update(User emp);

    /**
     * 登录
     * @param emp
     * @return
     */
    User login(User emp);
}
