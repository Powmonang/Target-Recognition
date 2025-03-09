package com.defectscan.mapper;

import com.defectscan.entity.DefectType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgDefectTypeMapper {
    List<DefectType> findDefectType(DefectType a);
    void addDefectType(DefectType a);
}
