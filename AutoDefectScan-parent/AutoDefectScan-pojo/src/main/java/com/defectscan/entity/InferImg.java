package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InferImg {
    private String id;//逻辑主键
    private String oper; //操作者（ID）
    private String imgId; // 图像ID
    private String safeType;//隐患ID
    private String level;//隐患等级
    private String keyTime; //时间戳
    private String indexUrl; // 坐标文件路径
    private String reportId; //安检报告ID
    //以下非数据库字段
    private String start;
    private String end;
}
