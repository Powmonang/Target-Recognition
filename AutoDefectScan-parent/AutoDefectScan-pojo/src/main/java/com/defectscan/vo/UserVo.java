package com.defectscan.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    // 登录时输入的用户名以及密码
    private String username;
    private String password;
}
