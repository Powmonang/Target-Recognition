package com.defectscan.service;

import com.defectscan.entity.ImgType;

import java.util.List;

public interface ImgTypeService {
    List<ImgType> findImgType(ImgType a);
    void addImgType(ImgType a);
}
