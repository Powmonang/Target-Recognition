package com.defectscan.service.impl;

import com.defectscan.entity.Model;
import com.defectscan.mapper.ModelMapper;
import com.defectscan.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ModelA implements ModelService {


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Model> findModels(Model a) {
        return modelMapper.findModels(a);
    }

    @Override
    public void addModel(Model a) {
        LocalDateTime now = LocalDateTime.now();
        a.setKeyTime(now.toString());
        modelMapper.addModel(a);
    }
}
