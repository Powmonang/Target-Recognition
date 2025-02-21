package com.lzk.service;

import com.lzk.entity.InferImg;
import com.lzk.entity.PageBean;

import java.util.List;

public interface InferImgService {
    void addInfer(InferImg a);
    List<InferImg>  findInfers(InferImg a);
    void changeInfer(InferImg a);
}
