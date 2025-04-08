package com.defectscan.vo;

import com.defectscan.entity.DefectInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiDetectReturnVO {
    private int id;                         // 文件id
    private String defectTime;              // 图片检测时间
    private int defectCount;             // 图片缺陷数量

    // 该图片的缺陷信息
    private List<DefectInfo> defectList = new ArrayList<>();
}
