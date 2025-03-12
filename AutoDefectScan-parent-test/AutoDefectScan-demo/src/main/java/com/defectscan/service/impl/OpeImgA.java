package com.defectscan.service.impl;

import com.defectscan.entity.OpeImg;
import com.defectscan.entity.SafeType;
import com.defectscan.mapper.OpeImgMapper;
import com.defectscan.mapper.SafeTypeMapper;
import com.defectscan.service.OpeImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpeImgA implements OpeImgService {
    @Autowired
    OpeImgMapper opeImgMapper;

    @Autowired
    SafeTypeMapper safeTypeMapper;

    @Override
    public void addOpeImg(OpeImg a) {
        opeImgMapper.addOpeImg(a);
    }

    @Override
    public void changeOpeImg(OpeImg a) {
        opeImgMapper.changeOpeImg(a);
    }



    @Override
    public List<OpeImg> findOpeImgs(OpeImg a) {
        return opeImgMapper.findOpeImgs(a);
    }

    @Override
    public void delOpeImg(OpeImg a) {
        opeImgMapper.delOpeImg(a);
    }

    @Override
    public List<OpeImg> getSampleHiddens() {
        List<SafeType> safeTypes = safeTypeMapper.findSafeType(null);
        Map<String, String> safeTypeMap = new HashMap<>();
        for(SafeType safeType : safeTypes){
            safeTypeMap.put(safeType.getId(), safeType.getType());
        }
        List<OpeImg> opeImgs = opeImgMapper.getSampleHiddens();
        for(OpeImg opeImg : opeImgs){
            opeImg.setSafeType(safeTypeMap.get(opeImg.getSafeType()));
        }
        return opeImgs;
    }

    @Override
    public List<String> getSampleHiddenIds() {
        List<String> ids = new ArrayList<>();
        List<OpeImg> opeImgs = opeImgMapper.getSampleHiddens();
        for(OpeImg opeImg : opeImgs){
            ids.add(opeImg.getSafeType());
        }
        return ids;
    }
}
