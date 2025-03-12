package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgSafeMap {
    private Integer id; //逻辑自增主键
    private String imgTypeId; //图片类型id
    private String safeTypeId; //隐患类别id等价于安检项目
    private String model; // 算法模型名称
    private String remark; //备注
}
