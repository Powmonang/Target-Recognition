package com.defectscan.service.impl;

import com.defectscan.entity.Img;
import com.defectscan.mapper.ImgMapper;
import com.defectscan.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ImgServiceImpl implements ImgService {

    @Autowired
    ImgMapper imgMapper;


    @Override
    public void addImg(Img a) {
        a.setCreateTime(LocalDateTime.now().toString());
        a.setUpdateTime(LocalDateTime.now().toString());
        imgMapper.addImg(a);
    }

}
