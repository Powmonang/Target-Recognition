package com.lzk.service.WarnType;

import com.lzk.entity.WarnType;
import com.lzk.mapper.WarnTypeMapper;
import com.lzk.service.WarnTypeService;
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
