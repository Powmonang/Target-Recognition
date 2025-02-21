package com.lzk.service.ImgSafeMap;

import com.lzk.entity.ImgSafeMap;
import com.lzk.entity.ImgType;
import com.lzk.entity.SafeType;
import com.lzk.entity.vo.ImgSafeMapVo;
import com.lzk.mapper.ImgSafeMapMapper;
import com.lzk.service.ImgSafeMapService;
import com.lzk.service.ImgTypeService;
import com.lzk.service.SafeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImgSafeMapA implements ImgSafeMapService {
    @Autowired
    private ImgSafeMapMapper imgSafeMapMapper;

    @Autowired
    private ImgTypeService imgTypeService;

    @Autowired
    private SafeTypeService safeTypeService;

    @Override
    public ImgSafeMapVo findImgSafeMap(ImgSafeMap imgSafeMap){
        Map<String, List<SafeType>> result = new HashMap<>();
        List<String> resultKeys = new java.util.ArrayList<>();

        // 先去查询imgType的id与type的对应
        List<ImgType> imgTypeList = imgTypeService.findImgType(null);
        Map<String,String> imgTypeMap = new HashMap<>();
        for (ImgType imgType : imgTypeList) {
            imgTypeMap.put(imgType.getId(),imgType.getType());
        }
        //再去查询safeType的id与type的对应
//        List<SafeType> safeTypeList = safeTypeService.findSafeType(null);
//        Map<String,String> safeTypeMap = new HashMap<>();
//        for(SafeType safeType : safeTypeList) {
//            safeTypeMap.put(safeType.getId(), safeType.getType());
//        }
        // 查询对照表里面的内容
        List<ImgSafeMap> imgSafeMaps = imgSafeMapMapper.findImgSafeMap(imgSafeMap);
        for (ImgSafeMap i : imgSafeMaps) {
            String imgTypeId = i.getImgTypeId();
            String imgType = imgTypeMap.get(i.getImgTypeId());

            SafeType st = new SafeType();
            st.setId(i.getSafeTypeId());
            //查询对应ID的SafeType
            st = safeTypeService.findSafeType(st).get(0);
            st.setModel(i.getModel());
            // 首先要判断这个图片类型是不是在那个Map里面
            List<SafeType> nowTp;
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
        return new ImgSafeMapVo(result, resultKeys);
    }

    @Override
    public void addImgSafeMap(ImgSafeMap imgSafeMap) {
        imgSafeMapMapper.addImgSafeMap(imgSafeMap);
    }

    @Override
    public void delImgSafeMap(ImgSafeMap imgSafeMap) {
        imgSafeMapMapper.delImgSafeMap(imgSafeMap);
    }
}
