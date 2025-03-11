package com.defectscan.controller;

import com.defectscan.entity.Result;
import com.defectscan.entity.WarnType;
import com.defectscan.service.WarnTypeService;
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
public class warnType {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.warnType");
    @Autowired
    private WarnTypeService warnTypeService;
    @PostMapping("/findWarnTypes")
    public Result findWarnTypes(@RequestBody WarnType request) {
        logger.info("查询告警类型"+ request);
        return Result.success(warnTypeService.findWarnTypes(request));
    }
}
