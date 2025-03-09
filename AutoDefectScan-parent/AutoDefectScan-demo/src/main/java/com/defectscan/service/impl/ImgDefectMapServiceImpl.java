package com.defectscan.service.impl;

import com.defectscan.entity.ImgDefectMap;
import com.defectscan.entity.ImgType;
import com.defectscan.entity.DefectType;
import com.defectscan.vo.ImgDefectMapVo;
import com.defectscan.mapper.ImgDefectMapMapper;
import com.defectscan.service.ImgDefectMapService;
import com.defectscan.service.ImgTypeService;
import com.defectscan.service.ImgDefectTypeService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImgDefectMapServiceImpl implements ImgDefectMapService {
    @Autowired
    private ImgDefectMapMapper imgDefectMapMapper;

    @Autowired
    private ImgTypeService imgTypeService;

    @Autowired
    private ImgDefectTypeService imgDefectTypeService;

    @Override
    public ImgDefectMapVo findImgDefectMap(ImgDefectMap imgDefectMap){
        Map<Integer, List<DefectType>> result = new HashMap<>();
        List<String> resultKeys = new java.util.ArrayList<>();

        // 先去查询imgType的id与type的对应
        List<ImgType> imgTypeList = imgTypeService.findImgType(null);
        Map<Integer,String> imgTypeMap = new HashMap<>();
        for (ImgType imgType : imgTypeList) {
            imgTypeMap.put(imgType.getId(),imgType.getType());
        }
        //再去查询DefectType的id与type的对应
//        List<DefectType> DefectTypeList = DefectTypeService.findDefectType(null);
//        Map<String,String> DefectTypeMap = new HashMap<>();
//        for(DefectType DefectType : DefectTypeList) {
//            DefectTypeMap.put(DefectType.getId(), DefectType.getType());
//        }
        // 查询对照表里面的内容(通过图片类型id查询)
        List<ImgDefectMap> imgDefectMaps = imgDefectMapMapper.findImgDefectMap(imgDefectMap);
        for (ImgDefectMap i : imgDefectMaps) {
            // 拿出图片类型id得到图片类型
            int imgTypeId = i.getImgTypeId();
            String imgType = imgTypeMap.get(imgTypeId);

            // 拿到缺陷类型id
            DefectType st = new DefectType();
            st.setId(i.getDefectTypeId());
            //查询对应ID的DefectType
            st = imgDefectTypeService.findDefectType(st).get(0);

            // 首先要判断这个图片类型是不是在那个Map里面
            List<DefectType> nowTp;
            if (result.containsKey(imgTypeId)){
                // 如果在里面了那么就直接拿到对应的
                nowTp = result.get(imgTypeId);
            }
            else {
                nowTp = new java.util.ArrayList<>();
                resultKeys.add(imgType);
            }
            nowTp.add(st);
            result.put(imgTypeId, nowTp);
        }
        return new ImgDefectMapVo(result, resultKeys);
    }

    @Override
    public void addImgDefectMap(ImgDefectMap imgDefectMap) {
        imgDefectMapMapper.addImgDefectMap(imgDefectMap);
    }

    @Override
    public void delImgDefectMap(ImgDefectMap imgDefectMap) {
        imgDefectMapMapper.delImgDefectMap(imgDefectMap);
    }
}
