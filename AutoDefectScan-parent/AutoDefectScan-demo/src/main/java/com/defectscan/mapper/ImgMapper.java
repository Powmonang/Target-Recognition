package com.defectscan.mapper;

import com.defectscan.dto.ImgPageQueryDTO;
import com.defectscan.entity.Img;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImgMapper {
    void addImg(Img a);

    Img selectImgByUrl(String url);

    void updateImgDetect(Img img);

    long pageSum(ImgPageQueryDTO imgPageQueryDTO);

    List<Img> pageQuery(int startIndex, int pageSize, @Param("a") ImgPageQueryDTO imgPageQueryDTO);

    Img findImgById(Long id);

    int isExist(int id);

    // 编辑图片
    void updateImg(Img img);

    // 删除图片
    void deleteImg(Img img);
}
