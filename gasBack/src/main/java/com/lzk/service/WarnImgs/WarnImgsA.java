package com.lzk.service.WarnImgs;

import com.lzk.entity.Img;
import com.lzk.entity.WarnImgs;
import com.lzk.entity.WarnType;
import com.lzk.entity.dto.ImgDTO;
import com.lzk.entity.vo.WarnImgsVo;
import com.lzk.mapper.ImgMapper;
import com.lzk.mapper.WarnImgsMapper;
import com.lzk.mapper.WarnTypeMapper;
import com.lzk.service.WarnImgsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WarnImgsA implements WarnImgsService {

    @Autowired
    private WarnImgsMapper warnImgsMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private WarnTypeMapper warnTypeMapper;

    @Override
    public List<WarnImgs> findWarnImgs(WarnImgs warnImgs) {
        return warnImgsMapper.findWarnImgs(warnImgs);
    }

    @Override
    public WarnImgsVo findWarnImgsByReportId(ImgDTO warnImgs) {
        //获取告警类型 并生成字典MAP
        WarnType warnType = new WarnType();
        List<WarnType> warnTypes = warnTypeMapper.findWarnTypes(warnType);
        Map<String, String> wanrTypeMap = new HashMap<>();
        for(WarnType type:warnTypes) {
            wanrTypeMap.put(type.getId(), type.getType());
        }
        WarnImgsVo result = new WarnImgsVo();
        List<List<WarnImgs>> resultRow = new ArrayList<>();
        //获取安检报告ID
        String reportId = warnImgs.getImg().getReportId();
        result.setReportId(reportId);
        //根据安检报告ID查询图片ID
        Img temp = new Img();
        temp.setReportId(reportId);
        List<Img> imgs = imgMapper.findImgs(temp);
        //遍历图片ID 查询告警记录
        for(Img img:imgs){
            WarnImgs tempWarn = new WarnImgs();
            tempWarn.setImgId(img.getId());
            List<WarnImgs> warnImgsList = warnImgsMapper.findWarnImgs(tempWarn);
            //把里面的告警项目从ID变为具体内容
            for(WarnImgs warn:warnImgsList){
                warn.setWarnType(wanrTypeMap.get(warn.getWarnType()));
            }
            if(!warnImgsList.isEmpty()){
                resultRow.add(warnImgsList);
            }
        }
        result.setRows(resultRow);
        return result;
    }

    @Override
    public void addWarnImgs(WarnImgs warnImgs) {
        //先查询有没有这个记录 如果有就不添加了
        // 图片ID+告警类型为唯一表示
        WarnImgs temp = new WarnImgs();
        temp.setImgId(warnImgs.getImgId());
        temp.setWarnType(warnImgs.getWarnType());
        List<WarnImgs> warnImgsList = warnImgsMapper.findWarnImgs(temp);
        if(warnImgsList.isEmpty()){
            warnImgsMapper.addWarnImgs(warnImgs);
        }

    }

    @Override
    public void delWarnImgs(WarnImgs warnImgs) {
        warnImgsMapper.delWarnImgs(warnImgs);
    }


}
