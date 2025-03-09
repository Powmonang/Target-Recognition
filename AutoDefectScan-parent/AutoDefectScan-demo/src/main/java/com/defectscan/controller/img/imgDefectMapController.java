package com.defectscan.controller.img;


import com.defectscan.entity.ImgDefectMap;
import com.defectscan.result.Result;
import com.defectscan.service.ImgDefectMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
@RequestMapping("/api/img")
@Slf4j
public class imgDefectMapController {
    @Autowired
    private ImgDefectMapService imgDefectMapService;

    /**
     * 查询图片的缺陷类型
     * @param rqt
     * @return
     */
    @PostMapping("/findImgDefectMap")
    public Result findImgDefectMap(@RequestBody ImgDefectMap rqt) {
        log.info("查询图片类型缺陷类型映射:{}", rqt);
        return Result.success(imgDefectMapService.findImgDefectMap(rqt));
    }



    @PostMapping("/addImgDefectMap")
    public Result addImgDefectMap(@RequestBody ImgDefectMap rqt) {
        log.info("添加图片类型缺陷类型映射:{}", rqt);
        imgDefectMapService.addImgDefectMap(rqt);
        return Result.success("添加成功！");
    }

    @PostMapping("/delImgDefectMap")
    public Result delImgDefectMap(@RequestBody ImgDefectMap rqt) {
        log.info("删除图片类型缺陷类型映射:{}", rqt);
        imgDefectMapService.delImgDefectMap(rqt);
        return Result.success("删除成功!");
    }

}
