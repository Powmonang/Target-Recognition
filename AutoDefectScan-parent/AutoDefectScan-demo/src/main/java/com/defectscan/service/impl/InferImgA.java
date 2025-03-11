package com.defectscan.service.impl;

import com.defectscan.entity.InferImg;
import com.defectscan.mapper.InferImgMapper;
import com.defectscan.service.InferImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InferImgA implements InferImgService {

    @Autowired
    InferImgMapper inferImgMapper;

    @Override
    public void addInfer(InferImg a) {
        inferImgMapper.addInfer(a);
    }

    @Override
    public List<InferImg> findInfers(InferImg a) {

        return inferImgMapper.findInfers(a);
    }

    @Override
    public void changeInfer(InferImg a) {
        inferImgMapper.changeInfer(a);
    }
}
