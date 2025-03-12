package com.defectscan.service.impl;

import com.defectscan.entity.WarnType;
import com.defectscan.mapper.WarnTypeMapper;
import com.defectscan.service.WarnTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WarnTypeA implements WarnTypeService {
    @Autowired
    private WarnTypeMapper warnTypeMapper;
    @Override
    public List<WarnType> findWarnTypes(WarnType warnType) {
        return warnTypeMapper.findWarnTypes(warnType);
    }
}
