package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgDefectMap {
    private int id; //逻辑自增主键
    private int imgTypeId; //图片类型id
    private int defectTypeId; //缺陷类别id
    //private String model; // 算法模型名称
    private String remark; //备注
}
