package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id; //ID
    private String username; //用户名
    private String password; //密码
    private String type; //权限
    private String name; //姓名
    private Short gender; //性别 , 1 男, 2 女
    private String image; //头像像url
    private String createTime; //创建时间
    private String updateTime; //修改时间
    private String createUser;        //创建人
    private String updateUser;        //修改人

}
