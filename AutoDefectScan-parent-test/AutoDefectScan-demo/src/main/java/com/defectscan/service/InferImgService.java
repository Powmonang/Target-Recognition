package com.defectscan.service;

import com.defectscan.entity.InferImg;

import java.util.List;

public interface InferImgService {
    void addInfer(InferImg a);
    List<InferImg>  findInfers(InferImg a);
    void changeInfer(InferImg a);
}
