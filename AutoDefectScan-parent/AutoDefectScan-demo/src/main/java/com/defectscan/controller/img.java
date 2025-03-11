package com.defectscan.controller;

import com.defectscan.entity.Img;
import com.defectscan.entity.PageBean;
import com.defectscan.entity.Result;
import com.defectscan.dto.ImgDTO;
import com.defectscan.service.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class img {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.Img");
    @Autowired
    ImgService imgService;

    //分页查询
    @GetMapping("/findImgsPage")
    public Result findImgsPage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               String uploader,
                               String safeCount,
                               String uploadDate,
                               String type,
                               String isOpe,
                               String show,
                               String start,
                               String end,
                               String id
                               ){
        logger.info("分页查询，参数：{}，{}，{}，{}，{}, {}, {}, {},{},{},{}", page, pageSize, uploader, safeCount, uploadDate, type, isOpe, show, start, end,id);
        Img request = new Img();
        request.setSafeCount(safeCount);
        request.setPhotoTagId(type);
        request.setUploader(uploader);
        request.setUploadTime(uploadDate); // 只是输入了日期 没具体到时间 用于查询该天数据
        request.setIsOpe(isOpe); //该图片是否被批注过？
        request.setShow(show); //该图片是否软删除
        request.setId(id);
        request.setStart(start);
        request.setEnd(end);
        PageBean result = imgService.findImgsPage(request, page, pageSize);
        return Result.success(result);
    }

    @PostMapping("/findImgsGroupByReport")
    public Result findImgsGroupByReport(@RequestBody ImgDTO request){
        logger.info("按组分页查询"+ request);
        return Result.success(imgService.findImgGroupByReport(request));
    }

    @PostMapping("/findImgsGroupByReportAiView")
    public Result findImgsGroupByReportInAiView(@RequestBody ImgDTO request){
        logger.info("AiView分页查询"+ request);
        return Result.success(imgService.findImgGroupByReportAi(request));
    }

    @PostMapping("/findImgsPageInAiView")
    public Result findImgsPageInAiView(@RequestBody ImgDTO request){
        logger.info("AiView分页查询"+ request);
        return Result.success(imgService.findImgsPage(request));
    }


    @PostMapping("/delImg")
    public Result delImg(@RequestBody com.defectscan.entity.Img request){
        imgService.delImg(request);
        logger.info("删除图像："+request.getId());
        return Result.success("删除成功", null);
    }

    @PostMapping("/changeImg")
    public Result changeImg(@RequestBody com.defectscan.entity.Img request){
        imgService.changeImg(request);
        logger.info("修改图像："+request.getId());
        return Result.success("修改成功", null);
    }
}
