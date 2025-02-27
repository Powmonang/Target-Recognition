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
     * 新增用户
     * @param emp
     */
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            " values(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
    void insert(User emp);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @Select("select * from emp where  id = #{id}")
    User getById(Integer id);

    /**
     * 更新用户
     * @param emp
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(User emp);

    /**
     * 根据用户名和密码查询用户
     * @param emp
     * @return
     */
    @Select("select * from emp where username = #{username} and password = #{password}")
    User getByUsernameAndPassword(User emp);


}
