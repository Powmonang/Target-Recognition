package com.defectscan.mapper;

import com.defectscan.entity.WarnImgs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarnImgsMapper {
    List<WarnImgs> findWarnImgs(WarnImgs warnImgs);
    void addWarnImgs(WarnImgs warnImgs);
    void delWarnImgs(WarnImgs warnImgs);
}
