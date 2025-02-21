package com.lzk.mapper;

import com.lzk.entity.ImgSafeMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgSafeMapMapper {
    List<ImgSafeMap> findImgSafeMap(ImgSafeMap imgSafeMap);
    void addImgSafeMap(ImgSafeMap imgSafeMap);
    void delImgSafeMap(ImgSafeMap imgSafeMap);
}
