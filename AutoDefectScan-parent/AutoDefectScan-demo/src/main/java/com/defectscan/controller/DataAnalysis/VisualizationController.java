package com.defectscan.controller.DataAnalysis;


import com.defectscan.result.Result;
import com.defectscan.service.VisualizationService;
import com.defectscan.vo.VisualizationDataVO;
import com.defectscan.vo.VisualizationIdVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/DataAnalysis")
@CrossOrigin("*") // 允许跨域
@Api(tags = "检测信息统计分析")
@Slf4j
public class VisualizationController {

    @Autowired
    VisualizationService visualizationService;


    @PostMapping("/visualization")
    public Result visualization(@RequestBody VisualizationIdVO visualizationIdVO) {
        log.info("开始进行可视化数据分析,分析图片id:{}", visualizationIdVO);
        try{
            VisualizationDataVO visualizationDataVO;
            visualizationDataVO = visualizationService.getVisualizationData(visualizationIdVO.getIds());
            return Result.success(visualizationDataVO);
        }catch (Exception e){
            log.info("分析失败:{}",e.getMessage());
            return Result.error(e.getMessage());
        }
    }

}
