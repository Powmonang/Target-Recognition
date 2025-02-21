package com.lzk.service;

import com.lzk.entity.Model;

import java.util.List;

public interface ModelService {
    List<Model> findModels(Model a);
    void addModel(Model a);
}
