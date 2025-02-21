package com.lzk.controller;

import com.lzk.entity.ImgType;
import com.lzk.entity.Result;
import com.lzk.service.ImgTypeService;
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
public class imgType {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.lzk.controller.ImgType");

    @Autowired
    ImgTypeService imgTypeService;
    
    @PostMapping("/findImgType")
    public Result findImgType(@RequestBody ImgType request){
        logger.info("查询图片类型："+ request.toString());
        return Result.success(imgTypeService.findImgType(request));
    }
}
