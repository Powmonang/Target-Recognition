package com.defectscan.vo;

import com.defectscan.entity.DefectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgDefectMapVo {
    private Map<Integer, List<DefectType>> imgDefectMap; //以图片内容ID为key
    private List<String> imgType; //图片类型内容
}
