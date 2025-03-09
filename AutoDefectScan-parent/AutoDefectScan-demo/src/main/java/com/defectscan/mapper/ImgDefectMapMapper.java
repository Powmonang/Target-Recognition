package com.defectscan.mapper;

import com.defectscan.entity.ImgDefectMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgDefectMapMapper {
    List<ImgDefectMap> findImgDefectMap(ImgDefectMap imgDefectMap);
    void addImgDefectMap(ImgDefectMap imgDefectMap);
    void delImgDefectMap(ImgDefectMap imgDefectMap);
}
