package com.defectscan.service.impl;

import com.defectscan.constant.DefectType;
import com.defectscan.entity.DefectInfo;
import com.defectscan.mapper.VisualizationMapper;
import com.defectscan.service.VisualizationService;
import com.defectscan.vo.VisualizationDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Component
public class VisualizationServiceImpl implements VisualizationService {

    @Autowired
    VisualizationMapper visualizationMapper;


    @Override
    public VisualizationDataVO getVisualizationData(List<Integer> imgIds) {
        VisualizationDataVO visualizationDataVO = new VisualizationDataVO();

        // 初始化 defectTypeCountMap，将所有缺陷类型初始数量设为 0
        Map<String, Integer> defectTypeCountMap = new HashMap<>();
        Map<Integer, String> allDefectTypes = DefectType.getDefectTypeMap();
        for (Map.Entry<Integer, String> entry : allDefectTypes.entrySet()) {
            defectTypeCountMap.put(entry.getValue(), 0);
        }

        // 初始化图片缺陷数量分布和缺陷类型平均置信度所需的 Map
        Map<Integer, Integer> defectCountImageCountMap = new HashMap<>();
        // 初始化 0 缺陷的图片数量为 0
        defectCountImageCountMap.put(0, 0);
        Map<Integer, Integer> imgDefectCount = new HashMap<>();
        Map<String, Double> defectTypeTotalConfidence = new HashMap<>();
        Map<String, Integer> defectTypeCount = new HashMap<>();

        List<DefectInfo> defectInfos = visualizationMapper.getDefectInfosByImgIds(imgIds);
        for (DefectInfo defectInfo : defectInfos) {
            String defectName = defectInfo.getDefectName();
            int imgId = defectInfo.getImgId();
            double confidence = Double.parseDouble(defectInfo.getConfidenceLevel());

            // 计算缺陷类型的数量分布
            if (defectTypeCountMap.containsKey(defectName)) {
                defectTypeCountMap.put(defectName, defectTypeCountMap.get(defectName) + 1);
            }

            // 计算图片缺陷数量分布
            int currentImgDefectCount = imgDefectCount.getOrDefault(imgId, 0) + 1;
            imgDefectCount.put(imgId, currentImgDefectCount);

            // 更新缺陷类型的总置信度和数量
            defectTypeTotalConfidence.put(defectName, defectTypeTotalConfidence.getOrDefault(defectName, 0.0) + confidence);
            defectTypeCount.put(defectName, defectTypeCount.getOrDefault(defectName, 0) + 1);
        }

        // 统计每个缺陷数量对应的图片数量
        for (int imgId : imgIds) {
            int defectCount = imgDefectCount.getOrDefault(imgId, 0);
            defectCountImageCountMap.put(defectCount, defectCountImageCountMap.getOrDefault(defectCount, 0) + 1);
        }

        // 确保 defectCountImageCountMap 键连续
        if (!defectCountImageCountMap.isEmpty()) {
            int maxKey = Collections.max(defectCountImageCountMap.keySet());
            for (int i = 1; i <= maxKey; i++) {
                defectCountImageCountMap.putIfAbsent(i, 0);
            }
        }

        // 完成缺陷类型平均置信度的计算
        Map<String, Double> defectTypeAverageConfidenceMap = new HashMap<>();
        // 初始化所有缺陷类型的平均置信度为 0.0
        for (Map.Entry<Integer, String> entry : allDefectTypes.entrySet()) {
            defectTypeAverageConfidenceMap.put(entry.getValue(), 0.0);
        }
        for (Map.Entry<String, Double> entry : defectTypeTotalConfidence.entrySet()) {
            String defectName = entry.getKey();
            double totalConfidence = entry.getValue();
            int count = defectTypeCount.get(defectName);
            defectTypeAverageConfidenceMap.put(defectName, totalConfidence / count);
        }

        visualizationDataVO.setDefectTypeCountMap(defectTypeCountMap);
        visualizationDataVO.setDefectCountImageCountMap(defectCountImageCountMap);
        visualizationDataVO.setDefectTypeAverageConfidenceMap(defectTypeAverageConfidenceMap);

        return visualizationDataVO;
    }
}
