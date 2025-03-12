package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Img {
    private int id;                     // 文件id
    private String imageName;           // 文件名
    private String originalLocalUrl;    // 源文件本地url
    private String originalAliyunUrl;   // 源文件云端url
    private String detectLocalUrl;      // 新文件本地url
    private String detectAliyunUrl;     // 新文件阿里云url

    private String detectTime;          // 模型检测时间
    private String defectTagId;         // 图片缺陷类型id,多种缺陷由逗号分隔
    private int defectCount;            // 图片缺陷数量. -1：未判断 0：无缺陷
    private float confidenceLevel;      // 图片检测置信度

    private String createTime;          // 创建时间
    private String updateTime;          // 修改时间
    private String createUser;          // 创建人
    private String updateUser;          // 修改人
    private String mark;                // 备注
    private int isOpe;               // 是否被标注了
    private int isShow;                // 软删除，是否展示;
}

//    // 非数据库字段
//    private String isJude;//是否被诊断了
//    private List<OpeImg> opeImgs;//图像的标注记录
//    private List<InferImg> inferImgs;//图像的推理记录
//    private String start;//起始时间
//    private String end;//结束时间

