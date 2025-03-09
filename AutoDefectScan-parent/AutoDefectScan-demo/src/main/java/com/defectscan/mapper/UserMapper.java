package com.defectscan.mapper;

import com.defectscan.annotation.AutoFill;
import com.defectscan.entity.User;
import com.defectscan.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 用户管理
 */
@Mapper
public interface UserMapper {

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 更新用户
     * @param user
     */
    //@AutoFill(value = OperationType.UPDATE)
    void updateUser(User user);

    /**
     * 检查该用户是否存在
     * @param username
     * @return
     */
    int checkUsernameExists(String username);

    /**
     * 新增（注册）用户
     * @param user
     */
    //@AutoFill(value = OperationType.INSERT)
    void addUser(User user);








    /**
     * 查询总记录数
     * @return
     */
    //@Select("select count(*) from emp")
    //public Long count();


    /**
     * 用户信息查询
     * @return
     */
    //@Select("select * from emp")
    public List<User> list(String name, Short gender, LocalDate begin, LocalDate end);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);







    /**
     * 根据用户名和密码查询用户
     * @param emp
     * @return
     */
    @Select("select * from emp where username = #{username} and password = #{password}")
    User getByUsernameAndPassword(User emp);


}
