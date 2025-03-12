package com.defectscan.service;

import com.defectscan.entity.WarnType;

import java.util.List;

public interface WarnTypeService {
    List<WarnType> findWarnTypes(WarnType warnType);
}
