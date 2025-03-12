package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarnImgs {
    private String id; // 逻辑主键
    private String oper; //告警ID
    private String imgId; //图像ID
    private String warnType;//告警ID
    private String keyTime; // 时间戳
    private String flag; //无实际意义
}
