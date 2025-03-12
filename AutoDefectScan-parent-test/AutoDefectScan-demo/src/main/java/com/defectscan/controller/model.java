package com.defectscan.controller;

import com.defectscan.entity.Model;
import com.defectscan.entity.Result;
import com.defectscan.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
@RequestMapping("/api/model")
public class model {

    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.model");
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
