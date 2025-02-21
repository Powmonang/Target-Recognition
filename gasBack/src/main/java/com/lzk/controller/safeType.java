package com.lzk.controller;

import com.lzk.entity.Result;
import com.lzk.entity.SafeType;
import com.lzk.service.SafeTypeService;
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
public class safeType {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.lzk.controller.safeType");

    @Autowired
    SafeTypeService safeTypeService;

    @PostMapping("/findSafeType")
    public Result findSafeType(@RequestBody SafeType request){
        logger.info("查询隐患类型："+ request.toString());
        return Result.success(safeTypeService.findSafeType(request));
    }

    @PostMapping("/addSafeType")
    public Result addSafeType(@RequestBody SafeType request){
        logger.info("添加隐患类型："+ request.toString());
        safeTypeService.addSafeType(request);
        return Result.success("添加成功！", null);
    }
}
