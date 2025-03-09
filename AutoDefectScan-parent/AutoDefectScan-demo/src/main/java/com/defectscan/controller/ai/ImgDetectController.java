package com.defectscan.controller.ai;


import com.defectscan.annotation.Log;
import com.defectscan.dto.ImgDetectListDTO;
import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.result.Result;
import com.defectscan.service.ImgDetectService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin("*") // 允许跨域
@Api(tags = "与ai模型对接接口")
@Slf4j
public class ImgDetectController {

    @Autowired
    private ImgDetectService aiDetectService;

    //@Log
    @PostMapping("/detect")
    public Result detect(@RequestBody UrlRequestDTO request){
        log.info("请求模型推理");
        try {
            ReturnDetectData returnDetectData = aiDetectService.detectImages(request);
            if(returnDetectData == null){
                return Result.error("返回结果为空");
            }
            log.info("图片已生成结果:{}",returnDetectData);
            return Result.success(returnDetectData);
        } catch (Exception e) {
            log.info("图片检测失败，{}", e.toString());
            return Result.error("图片检测失败：" + e.getMessage());
        }
    }
}
