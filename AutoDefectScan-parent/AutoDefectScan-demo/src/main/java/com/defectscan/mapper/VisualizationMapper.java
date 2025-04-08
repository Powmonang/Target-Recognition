package com.defectscan.mapper;

import com.defectscan.entity.DefectInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface VisualizationMapper {
    List<DefectInfo> getDefectInfosByImgIds(List<Integer> imgIds);
}