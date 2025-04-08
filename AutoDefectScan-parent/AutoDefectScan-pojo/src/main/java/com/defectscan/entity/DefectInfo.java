package com.defectscan.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  图片缺陷检测信息（缺陷坐标+置信度）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectInfo {
    private int id;
    private int imgId;
    private int defectId;
    private String defectName;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private String confidenceLevel;
}
