package com.defectscan.entity;

import com.defectscan.vo.AiDetectReturnVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDetectData {
    int code;
    String msg;
    String requestTime;
    List<AiDetectReturnVO> detectImgList;
}
