package com.lzk.service.ImgType;

import com.lzk.entity.ImgType;
import com.lzk.mapper.ImgTypeMapper;
import com.lzk.service.ImgTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TypeA implements ImgTypeService {

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
