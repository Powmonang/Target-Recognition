package com.defectscan.dto;

import com.defectscan.entity.ImgOrigin;
//import com.defectscan.entity.InferImg;
//import com.defectscan.entity.OpeImg;
//import com.defectscan.entity.WarnImgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgDTO {
    private Integer page;//起始页
    private Integer pageSize;//页数
    //private InferImg inferImg; // 隐患推理对象
    private ImgOrigin img;//图片对象
    //private OpeImg opeImg;// 图片打标对象
    //private WarnImgs warnImgs;// 图片告警对象


}


