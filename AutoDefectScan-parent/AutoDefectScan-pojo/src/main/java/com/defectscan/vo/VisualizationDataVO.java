package com.defectscan.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizationDataVO {
    // 使用Map来存储缺陷类型和对应的数量，键为缺陷类型，值为数量
    private Map<String, Integer> defectTypeCountMap = new HashMap<>();
    // 使用Map来存储缺陷数量和对应的图片数量，键为缺陷数量，值为图片数量
    private Map<Integer, Integer> defectCountImageCountMap = new HashMap<>();
    // 使用Map来存储缺陷类型和对应的平均置信度，键为缺陷类型，值为平均置信度
    private Map<String, Double> defectTypeAverageConfidenceMap = new HashMap<>();

}
