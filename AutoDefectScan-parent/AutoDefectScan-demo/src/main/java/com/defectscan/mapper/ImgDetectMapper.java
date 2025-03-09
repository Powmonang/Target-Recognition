package com.defectscan.mapper;


import com.defectscan.annotation.AutoFill;
import com.defectscan.dto.ImgDetectListDTO;
import com.defectscan.dto.OriginAndDetect;

import com.defectscan.entity.ImgDetectData;
import com.defectscan.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgDetectMapper {

    OriginAndDetect selectOriginIdByUrl(String url);

    //@AutoFill(value = OperationType.INSERT)
    void insertImgDetectList(List<ImgDetectData> aiDetectListDTO);
}
