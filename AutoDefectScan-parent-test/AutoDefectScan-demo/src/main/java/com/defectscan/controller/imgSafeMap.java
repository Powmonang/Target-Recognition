package com.defectscan.controller;


import com.defectscan.entity.ImgSafeMap;
import com.defectscan.entity.Result;
import com.defectscan.service.ImgSafeMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
@RequestMapping("/api/img")
public class imgSafeMap {
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.imgSafeMap");
    @Autowired
    private ImgSafeMapService imgSafeMapService;
    @PostMapping("/findImgSafeMap")
    public Result findImgSafeMap(@RequestBody ImgSafeMap rqt) {
        logger.info("查询图片类型隐患类型映射:{}", rqt);
        return Result.success(imgSafeMapService.findImgSafeMap(rqt));
    }

    @PostMapping("/addImgSafeMap")
    public Result addImgSafeMap(@RequestBody ImgSafeMap rqt) {
        logger.info("添加图片类型隐患类型映射:{}", rqt);
        imgSafeMapService.addImgSafeMap(rqt);
        return Result.success("添加成功！");
    }

    @PostMapping("/delImgSafeMap")
    public Result delImgSafeMap(@RequestBody ImgSafeMap rqt) {
        logger.info("删除图片类型隐患类型映射:{}", rqt);
        imgSafeMapService.delImgSafeMap(rqt);
        return Result.success("删除成功!");
    }

}
