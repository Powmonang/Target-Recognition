package com.lzk.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String id; //账号
    private String ps; //密码
    private String type; //权限
    private String name; //姓名
    private String sex; //性别
    private String phe; //电话
    private String finalLogin;  //最后登录时间
}
