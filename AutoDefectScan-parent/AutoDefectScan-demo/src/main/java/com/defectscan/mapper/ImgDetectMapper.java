package com.defectscan.mapper;


import com.defectscan.dto.ImgDetectListDTO;
import com.defectscan.dto.OriginAndDetect;

import com.defectscan.entity.ImgDetectData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgDetectMapper {

    OriginAndDetect selectOriginIdByUrl(String url);

    void insertImgDetectList(List<ImgDetectData> aiDetectListDTO);
}
