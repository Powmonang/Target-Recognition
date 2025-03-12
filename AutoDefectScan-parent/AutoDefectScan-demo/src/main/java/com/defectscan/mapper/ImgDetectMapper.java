package com.defectscan.mapper;


import com.defectscan.entity.Img;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgDetectMapper {

    Img selectImgByUrl(String url);

    //@AutoFill(value = OperationType.INSERT)
    void updateImgDetectList(List<Img> detectImgList);
}
