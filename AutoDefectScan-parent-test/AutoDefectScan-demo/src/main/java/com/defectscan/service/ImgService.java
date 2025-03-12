package com.defectscan.service;

import com.defectscan.entity.Img;
import com.defectscan.entity.PageBean;
import com.defectscan.dto.ImgDTO;
import com.defectscan.vo.ImgVo;

import java.util.List;


public interface ImgService {
    void addImg(Img a);
    List<Img> findImgs(Img a);
    PageBean findImgsPage(Img a, Integer page, Integer pageSize);

    PageBean findImgsPage(ImgDTO a);

    PageBean findReportPage(ImgDTO a);
    List<String> findReport(Img a);
    ImgVo findImgGroupByReport(ImgDTO imgDTO);

    ImgVo findImgGroupByReportAi(ImgDTO imgDTO);

    void delImg(Img a);
    void changeImg(Img a);
}
