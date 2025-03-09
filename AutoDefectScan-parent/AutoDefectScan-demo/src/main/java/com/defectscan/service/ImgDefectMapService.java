package com.defectscan.service;

import com.defectscan.entity.ImgDefectMap;
import com.defectscan.vo.ImgDefectMapVo;

public interface ImgDefectMapService {
    ImgDefectMapVo findImgDefectMap(ImgDefectMap imgDefectMap);
    void addImgDefectMap(ImgDefectMap imgDefectMap);
    void delImgDefectMap(ImgDefectMap imgDefectMap);
}
