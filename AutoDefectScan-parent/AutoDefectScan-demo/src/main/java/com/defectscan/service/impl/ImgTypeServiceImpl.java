package com.defectscan.service.impl;

import com.defectscan.entity.ImgType;
import com.defectscan.mapper.ImgTypeMapper;
import com.defectscan.service.ImgTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImgTypeServiceImpl implements ImgTypeService {

    @Autowired
    ImgTypeMapper imgTypeMapper;
    @Override
    public List<ImgType> findImgType(ImgType a) {
        return imgTypeMapper.findImgType(a);
    }

    @Override
    public void addImgType(ImgType a) {
        imgTypeMapper.addImgType(a);
    }
}
