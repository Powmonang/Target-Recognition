package com.defectscan.mapper;

import com.defectscan.entity.Img;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImgMapper {
    void addImg(Img a);
}
