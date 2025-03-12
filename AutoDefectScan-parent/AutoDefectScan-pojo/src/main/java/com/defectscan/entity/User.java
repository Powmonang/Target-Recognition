package com.defectscan.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String username; //账号
    private String password; //密码
    private String userType; //权限
    private String name; //姓名
    private String sex; //性别
    private String phone; //电话
    private String finalLogin;  //最后登录时间
}
