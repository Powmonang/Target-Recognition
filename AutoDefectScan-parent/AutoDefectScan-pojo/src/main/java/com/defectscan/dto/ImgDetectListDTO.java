package com.defectscan.dto;

import com.defectscan.entity.ImgDetectData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class ImgDetectListDTO {
    List<ImgDetectData> imgDetectListDTO2;

    public ImgDetectListDTO() {
        this.imgDetectListDTO2 = new ArrayList<ImgDetectData>();
    }
}
