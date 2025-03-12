package com.defectscan.service;

import com.defectscan.entity.ImgSafeMap;
import com.defectscan.vo.ImgSafeMapVo;

public interface ImgSafeMapService {
    ImgSafeMapVo findImgSafeMap(ImgSafeMap imgSafeMap);
    void addImgSafeMap(ImgSafeMap imgSafeMap);
    void delImgSafeMap(ImgSafeMap imgSafeMap);
}
