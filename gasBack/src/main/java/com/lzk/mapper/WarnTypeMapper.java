package com.lzk.mapper;

import com.lzk.entity.WarnType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarnTypeMapper {
    List<WarnType> findWarnTypes(WarnType a);
}
