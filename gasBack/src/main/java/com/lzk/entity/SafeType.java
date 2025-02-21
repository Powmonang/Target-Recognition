package com.lzk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SafeType {
    private String id; // 隐患ID
    private String type; // 类别名称
    private String content; // 内容
    private String mark;//备注
    //非数据库字段
    private String model;//诊断模型
}
