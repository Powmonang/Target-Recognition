package com.defectscan.service;

import com.defectscan.entity.SafeType;

import java.util.List;

public interface SafeTypeService {
    List<SafeType> findSafeType(SafeType a);
    void addSafeType(SafeType a);
}
