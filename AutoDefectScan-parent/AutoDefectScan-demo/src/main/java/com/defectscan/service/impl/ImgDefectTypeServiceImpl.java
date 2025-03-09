package com.defectscan.service.impl;

import com.defectscan.entity.DefectType;
import com.defectscan.mapper.ImgDefectTypeMapper;
import com.defectscan.service.ImgDefectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImgDefectTypeServiceImpl implements ImgDefectTypeService {

    @Autowired
    ImgDefectTypeMapper imgDefectTypeMapper;

    @Override
    public List<DefectType> findDefectType(DefectType a) {
        return imgDefectTypeMapper.findDefectType(a);
    }

    @Override
    public void addDefectType(DefectType a) {
        //先查询所有的
        List<DefectType> DefectTypes = imgDefectTypeMapper.findDefectType(null);
        int maxId = 0;
        for(DefectType s : DefectTypes){
            if(s.getId() > maxId){
                maxId = s.getId();
            }
        }
        a.setId(maxId+1);
        imgDefectTypeMapper.addDefectType(a);
    }
}
