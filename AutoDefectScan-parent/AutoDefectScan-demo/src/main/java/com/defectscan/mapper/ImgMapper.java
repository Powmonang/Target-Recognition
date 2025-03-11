package com.defectscan.mapper;

import com.defectscan.entity.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImgMapper {
    List<Img> findImgsPage(@Param("a") Img a, Integer page, Integer pageSize);
    List<Img> findImgs(Img a);

    List<String> getAllReportIdPage(@Param("a") Img a, Integer page, Integer pageSize); //获取所有报修单id

    List<String> getAllReportId(Img a);
    void addImg(Img a);
    void delImg(Img a);
    void changeImg(Img a);
}
