package com.defectscan.service;

import com.defectscan.entity.WarnImgs;
import com.defectscan.dto.ImgDTO;
import com.defectscan.vo.WarnImgsVo;

import java.util.List;

public interface WarnImgsService {
    List<WarnImgs> findWarnImgs(WarnImgs warnImgs);
    WarnImgsVo findWarnImgsByReportId(ImgDTO warnImgs);

    void addWarnImgs(WarnImgs warnImgs);
    void delWarnImgs(WarnImgs warnImgs);
}
