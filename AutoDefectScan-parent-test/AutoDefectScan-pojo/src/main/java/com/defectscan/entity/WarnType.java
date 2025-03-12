package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarnType {
    private String id; // 安全告警ID
    private String type; //安全警告类别名称
    private String content; //内容
    private String mark; //备注

}
