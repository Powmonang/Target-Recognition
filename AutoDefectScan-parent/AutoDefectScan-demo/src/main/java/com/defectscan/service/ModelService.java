package com.defectscan.service;

import com.defectscan.entity.Model;

import java.util.List;

public interface ModelService {
    List<Model> findModels(Model a);
    void addModel(Model a);
}
