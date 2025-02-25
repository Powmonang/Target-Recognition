package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Img {
    private int id; //图像ID
    private String url; //图像URL
    private String imgName; //图像名称
    private String place; //图像采集地
    private String uploadTime; //图像上传时间
    private String uploader; // 图像上传者
    private String photoTagId; // 缺陷类别ID
    private String defectCount;// 缺陷数量
    private String finalMan; // 最后修改人ID
    private String finalTime; // 最后修改时间
    private String reportId;// 报告ID
    private String mark;// 备注
    private String isOpe;// 是否被标注了
    private String show; // 软删除 是否展示
    // 非数据库字段
    private String isJude;//是否被诊断了
    //private List<OpeImg> opeImgs;//图像的标注记录
    //private List<InferImg> inferImgs;//图像的推理记录
    private String start;//起始时间
    private String end;//结束时间
}
