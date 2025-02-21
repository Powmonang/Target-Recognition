package com.lzk.mapper;

import com.lzk.entity.SafeType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SafeTypeMapper {
    List<SafeType> findSafeType(SafeType a);
    void addSafeType(SafeType a);
}
