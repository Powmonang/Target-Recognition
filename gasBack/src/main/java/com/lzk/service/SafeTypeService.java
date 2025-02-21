package com.lzk.service;

import com.lzk.entity.SafeType;

import java.util.List;

public interface SafeTypeService {
    List<SafeType> findSafeType(SafeType a);
    void addSafeType(SafeType a);
}
