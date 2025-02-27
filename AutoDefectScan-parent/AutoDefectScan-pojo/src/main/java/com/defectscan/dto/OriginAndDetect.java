package com.defectscan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginAndDetect {
    /*
    用于将源图片与检测后图片公共的参数赋给 检测后图片
     */
    private int id;                 //图像ID
    private String localUrl;      //新文件本地url
    private String aliyunUrl;     //新文件阿里云url
    private String imgName;     //图像名称



}
