package com.defectscan.controller.ai;


import com.defectscan.dto.AiDetectDTO;
import com.defectscan.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/ai/detect")
@CrossOrigin("*") // 允许跨域
@Api(tags = "与ai模型对接接口")
@Slf4j
public class modelDetect {

    @Value("${controller.ai.detect}")
    private String detectUrl;

//    @Autowired
//    private RestTemplate restTemplate;
//
//
//
//    @PostMapping("/detect")
//    public Result detect(@RequestBody AiDetectDTO request){
//        log.info("请求模型推理"+ request);
//        HttpEntity<AiDetectDTO> request2 = new HttpEntity<>(request);
//        System.out.println(restTemplate.postForObject(detectUrl, request2, Result.class));
//        return Result.success();
//    }
}
