package com.lzk.service.InferImg;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lzk.entity.Img;
import com.lzk.entity.InferImg;
import com.lzk.entity.PageBean;
import com.lzk.mapper.InferImgMapper;
import com.lzk.service.InferImgService;
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
