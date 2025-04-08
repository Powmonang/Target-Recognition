package com.defectscan.service;

import com.defectscan.vo.VisualizationDataVO;

import java.util.List;

public interface VisualizationService {
    /**
     * 获取可视化信息
     * @param ids
     * @return
     */
    VisualizationDataVO getVisualizationData(List<Integer> ids);
}
