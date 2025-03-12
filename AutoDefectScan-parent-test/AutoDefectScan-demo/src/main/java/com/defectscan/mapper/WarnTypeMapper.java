package com.defectscan.mapper;

import com.defectscan.entity.WarnType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarnTypeMapper {
    List<WarnType> findWarnTypes(WarnType a);
}
