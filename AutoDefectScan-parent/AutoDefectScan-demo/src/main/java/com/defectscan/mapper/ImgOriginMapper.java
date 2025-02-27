package com.defectscan.mapper;

import com.defectscan.entity.ImgOrigin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImgOriginMapper {
//    List<Img> findImgsPage(@Param("a") Img a, Integer page, Integer pageSize);
//    List<Img> findImgs(Img a);
//
//    List<String> getAllReportIdPage(@Param("a") Img a, Integer page, Integer pageSize); //获取所有报修单id
//
//    List<String> getAllReportId(Img a);
    void addImgOrigin(ImgOrigin a);
//    void delImg(Img a);
//    void changeImg(Img a);
}
