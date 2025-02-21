package com.lzk.service;

import com.lzk.entity.ImgSafeMap;
import com.lzk.entity.vo.ImgSafeMapVo;

import java.util.List;

public interface ImgSafeMapService {
    ImgSafeMapVo findImgSafeMap(ImgSafeMap imgSafeMap);
    void addImgSafeMap(ImgSafeMap imgSafeMap);
    void delImgSafeMap(ImgSafeMap imgSafeMap);
}
