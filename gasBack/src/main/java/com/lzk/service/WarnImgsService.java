package com.lzk.service;

import com.lzk.entity.WarnImgs;
import com.lzk.entity.dto.ImgDTO;
import com.lzk.entity.vo.WarnImgsVo;

import java.util.List;

public interface WarnImgsService {
    List<WarnImgs> findWarnImgs(WarnImgs warnImgs);
    WarnImgsVo findWarnImgsByReportId(ImgDTO warnImgs);

    void addWarnImgs(WarnImgs warnImgs);
    void delWarnImgs(WarnImgs warnImgs);
}
