package com.lzk.service;

import com.lzk.entity.OpeImg;
import com.lzk.entity.SafeType;

import java.util.List;

public interface OpeImgService {
    void addOpeImg(OpeImg a);
    void changeOpeImg(OpeImg a);
    List<OpeImg> findOpeImgs(OpeImg a);

    void delOpeImg(OpeImg a);
    List<OpeImg> getSampleHiddens();
    List<String> getSampleHiddenIds();
}
