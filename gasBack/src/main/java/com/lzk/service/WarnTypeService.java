package com.lzk.service;

import com.lzk.entity.WarnType;

import java.util.List;

public interface WarnTypeService {
    List<WarnType> findWarnTypes(WarnType warnType);
}
