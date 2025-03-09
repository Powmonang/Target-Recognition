package com.defectscan.service;

import com.defectscan.entity.DefectType;

import java.util.List;

public interface ImgDefectTypeService {
    List<DefectType> findDefectType(DefectType a);
    void addDefectType(DefectType a);
}

