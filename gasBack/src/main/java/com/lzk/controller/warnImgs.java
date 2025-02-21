package com.lzk.controller;

import com.lzk.entity.Result;
import com.lzk.entity.WarnImgs;
import com.lzk.entity.dto.ImgDTO;
import com.lzk.service.WarnImgsService;
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
public class warnImgs {
    @Autowired
    private WarnImgsService warnImgsService;
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.lzk.controller.warnImgs");
    
    @PostMapping("/findWarnImgs")
    public Result findWarnImgs(@RequestBody WarnImgs request) {
        logger.info("查询报警图片信息"+request);
        return Result.success(warnImgsService.findWarnImgs(request));
    }

    @PostMapping("/findWarnImgsByReportId")
    public Result findWarnImgsByReportId(@RequestBody ImgDTO request) {
        logger.info("根据安检报告ID查询图片告警信息"+request);
        if(request.getImg()==null||request.getImg().getReportId()==null){
            return Result.error("请输入安检报告ID");
        }
        return Result.success(warnImgsService.findWarnImgsByReportId(request));
    }

    @PostMapping("/addWarnImgs")
    public Result addWarnImgs(@RequestBody WarnImgs request) {
        logger.info("添加报警图片信息"+request);
        warnImgsService.addWarnImgs(request);
        return Result.success();
    }

    @PostMapping("/delWarnImgs")
    public Result delWarnImgs(@RequestBody WarnImgs request) {
        logger.info("删除报警图片信息"+request);
        warnImgsService.delWarnImgs(request);
        return Result.success();
    }

}
