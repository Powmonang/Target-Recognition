package com.defectscan.service;

import com.defectscan.entity.OpeImg;

import java.util.List;

public interface OpeImgService {
    void addOpeImg(OpeImg a);
    void changeOpeImg(OpeImg a);
    List<OpeImg> findOpeImgs(OpeImg a);

    void delOpeImg(OpeImg a);
    List<OpeImg> getSampleHiddens();
    List<String> getSampleHiddenIds();
}
