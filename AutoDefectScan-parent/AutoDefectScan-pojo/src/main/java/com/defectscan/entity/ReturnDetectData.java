package com.defectscan.entity;

import com.defectscan.dto.ImgDetectListDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDetectData {
    int code;
    String msg;
    String requestTime;
    List<ImgDetectData> imgDetectListDTO;
}
