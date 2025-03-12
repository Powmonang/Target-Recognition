package com.defectscan.service;

import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.ReturnDetectData;

public interface ImgDetectService {
    public ReturnDetectData detectImages(UrlRequestDTO request);

}
