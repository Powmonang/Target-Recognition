package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgOrigin {
    private int id; //图像ID
    private String localUrl;      //新文件本地url
    private String aliyunUrl;     //新文件阿里云url
    private String imgName;     //图像名称
    private String createTime; //图像上传时间
    private String createUser; // 图像上传者
    private String updateUser; // 最后修改人
    private String updateTime; // 最后修改时间
    private String mark;// 备注


    //private String isOpe;// 是否被标注了
    //private String show; // 软删除 是否展示
    // 非数据库字段
    //private String isJude;//是否被诊断了
    //private List<OpeImg> opeImgs;//图像的标注记录
    //private List<InferImg> inferImgs;//图像的推理记录
    //private String start;//起始时间
    //private String end;//结束时间
}
