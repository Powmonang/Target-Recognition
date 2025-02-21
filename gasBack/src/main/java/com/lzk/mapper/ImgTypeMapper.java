package com.lzk.mapper;

import com.lzk.entity.ImgType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgTypeMapper {
    List<ImgType> findImgType(ImgType a);
    void addImgType(ImgType a);
}
