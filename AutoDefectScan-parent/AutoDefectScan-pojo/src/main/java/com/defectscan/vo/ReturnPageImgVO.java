package com.defectscan.vo;

import com.defectscan.entity.DefectInfo;
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
public class ReturnPageImgVO {
    private int id;                     // 文件id
    private String imageName;           // 文件名
    private String aliyunUrl;            // 阿里云路径
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 修改时间
    private String createUser;          // 创建人
    private String updateUser;          // 修改人
    private String mark;                // 备注

    private String detectTime;          // 图片检测时间
    private String defectCount;         // 图片
    private String defectType;          // 缺陷类型，有多个类型用，隔开

    // 该图片的缺陷信息
    private Map<String, List<DefectInfo>> defectMap = new HashMap<String, List<DefectInfo>>();
}
