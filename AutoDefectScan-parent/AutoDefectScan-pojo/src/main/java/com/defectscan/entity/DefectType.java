package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectType {
    private int id; // 缺陷ID
    private String type; // 类别名称
    private String content; // 内容
    private String mark;//备注
}
