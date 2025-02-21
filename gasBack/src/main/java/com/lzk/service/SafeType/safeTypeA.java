package com.lzk.service.SafeType;

import com.lzk.entity.SafeType;
import com.lzk.mapper.SafeTypeMapper;
import com.lzk.service.SafeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class safeTypeA implements SafeTypeService {

    @Autowired
    SafeTypeMapper safeTypeMapper;

    @Override
    public List<SafeType> findSafeType(SafeType a) {
        return safeTypeMapper.findSafeType(a);
    }

    @Override
    public void addSafeType(SafeType a) {
        //先查询所有的
        List<SafeType> safeTypes = safeTypeMapper.findSafeType(null);
        Integer maxId = 0;
        for(SafeType s : safeTypes){
            if(Integer.parseInt(s.getId())>maxId){
                maxId = Integer.valueOf(s.getId());
            }
        }
        a.setId(String.valueOf(maxId+1));
        safeTypeMapper.addSafeType(a);
    }
}
