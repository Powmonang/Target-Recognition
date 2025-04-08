package com.defectscan.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgPageQueryVO {
    private String start;               // 时间
    private String end;                 // 时间
    private String createUser;          // 创建人
    private String defectType;                // 缺陷类型(多种缺陷类型用，隔开）
    //private String defectTagId;         // 图片缺陷类型id,多种缺陷由逗号分隔
    //private String defectCount;            // 图片缺陷数量. -1：未判断 0：无缺陷
    //private String confidenceLevel;      // 图片检测置信度
    //private String isDetect;               // 是否检测
    //private String isOpe;                  // 是否被标注了
    //private String isBackup;                // 是否备份。
}
