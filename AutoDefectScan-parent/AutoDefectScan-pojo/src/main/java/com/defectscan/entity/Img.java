package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Img {
    private int id;                         // 文件id
    private String imageName;               // 文件名
    private String localDir;                // 文件本地路径
    private String aliyunUrl;                // 文件aliyunOSS路径
    private String backupUrl;               // 文件备份url

    private LocalDateTime createTime;       // 创建时间
    private LocalDateTime updateTime;       // 修改时间
    private String createUser;              // 创建人
    private String updateUser;              // 修改人
    private String mark;                    // 备注
    private String isDetect;                // 是否检测
    private String isOpe;                   // 是否被标注了
    private String isShow;                  // 是否展示;
    private String isBackup;                // 是否备份。


    private String detectTime;              // 图片检测时间
    private int defectCount;             // 图片缺陷数量
    private String defectTypeId;            // 缺陷类型，有多个类型用，隔开

    // 该图片的缺陷信息
    private Map<String, List<DefectInfo>> defectMap = new HashMap<String, List<DefectInfo>>();
}


