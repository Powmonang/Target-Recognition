package com.lzk.service;

import com.lzk.entity.ImgType;

import java.util.List;

public interface ImgTypeService {
    List<ImgType> findImgType(ImgType a);
    void addImgType(ImgType a);
}
