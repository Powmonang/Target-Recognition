package com.lzk.controller;

import com.lzk.entity.Model;
import com.lzk.entity.Result;
import com.lzk.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class model {

    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.lzk.controller.model");
    @Autowired
    private ModelService modelService;

    @PostMapping("/findModels")
    public Result findModels(@RequestBody Model request) {
        logger.info("查询模型列表："+request);
        return Result.success(modelService.findModels(request));
    }

    @PostMapping("/addModel")
    public Result addModel(@RequestBody Model request) {
        logger.info("添加模型："+request);
        modelService.addModel(request);
        return Result.success();
    }

}
