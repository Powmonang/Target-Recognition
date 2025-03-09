package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgDetectData {
    private int id;                     //新文件id
    private int originalId;             //源文件id
    private String originalLocalUrl;    //源文件本地url
    private String originalAliyunUrl;   //源文件云端url
    private String detectLocalUrl;      //新文件本地url
    private String detectAliyunUrl;     //新文件阿里云url
    private String defectImageName;     //新文件名（与源文件名相同）
    private String detectTime;          //模型检测时间
    private String defectTagId;         //图片缺陷类型id,多种缺陷由逗号分隔
    private int defectCount;            //图片缺陷数量. -1：未判断 0：无缺陷
    private float confidenceLevel;      //图片检测置信度
    private String createTime;   //创建时间
    private String updateTime;   //修改时间
    private String createUser;          //创建人
    private String updateUser;          //修改人
    private String mark;                // 备注

}
