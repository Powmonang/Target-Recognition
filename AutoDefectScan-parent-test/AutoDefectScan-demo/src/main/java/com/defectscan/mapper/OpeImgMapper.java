package com.defectscan.mapper;

import com.defectscan.entity.OpeImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpeImgMapper {
    void addOpeImg(OpeImg a);
    void changeOpeImg(OpeImg a);
    List<OpeImg> findOpeImgs(OpeImg a);

    void delOpeImg(OpeImg a);
    List<OpeImg> getSampleHiddens();
}
