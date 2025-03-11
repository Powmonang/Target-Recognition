package com.defectscan.controller;

import com.defectscan.entity.Img;
import com.defectscan.entity.Result;
import com.defectscan.dto.AiDetectDTO;
import com.defectscan.dto.TrainDTO;
import com.defectscan.service.AIService;
import com.defectscan.service.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class ai {
    @Value("${ai.detect}")
    private String detectUrl;

    @Autowired
    private ImgService imgService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AIService aiService;

    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.ai");
    @PostMapping("/detect")
    public Result detect(@RequestBody AiDetectDTO request){
        logger.info("请求模型推理"+ request);
        HttpEntity<AiDetectDTO> request2 = new HttpEntity<>(request);
        System.out.println(restTemplate.postForObject(detectUrl, request2, Result.class));
        return Result.success();
    }

    /**
     * 按照安检报告ID 来进行AI诊断
     */
    @PostMapping("/detectGroupByReport")
    public Result detectGroupByReport(@RequestBody AiDetectDTO request){
        logger.info("按组请求模型推理"+ request);
        // 存放请求体 图像ID数组
        List<String> imgIds = new ArrayList<>();
        //获取安检报告ID数组
        List<String> reportIds = request.getImageData();
        for(String reportId : reportIds){
            //根据安检报告ID查询图片ID
            Img temp = new Img();
            temp.setReportId(reportId);
            //获取到图片数组
            List <Img> imgs = imgService.findImgs(temp);
            for(Img img : imgs){
                imgIds.add(img.getId());
            }
        }
        // 替换请求体中的图像ID数组
        request.setImageData(imgIds);
        HttpEntity<AiDetectDTO> request2 = new HttpEntity<>(request);
        System.out.println(restTemplate.postForObject(detectUrl, request2, Result.class));
        return Result.success("推理成功");
    }

    @PostMapping("/detectAllGroupByReport")
    public Result detectAllGroupByReport(){
        //首先要查询所有的报告ID
        List<String> reportIds = imgService.findReport(null);
        // 存放请求体 图像ID数组
        List<String> imgIds = new ArrayList<>();
        AiDetectDTO request = new AiDetectDTO();
        for(String reportId : reportIds){
            //根据安检报告ID查询图片ID
            Img temp = new Img();
            temp.setReportId(reportId);
            //获取到图片数组
            List <Img> imgs = imgService.findImgs(temp);
            for(Img img : imgs){
                imgIds.add(img.getId());
            }
        }
        // 替换请求体中的图像ID数组
        request.setImageData(imgIds);
        HttpEntity<AiDetectDTO> request2 = new HttpEntity<>(request);
        restTemplate.postForObject(detectUrl, request2, Result.class);
        return Result.success("推理成功");
    }

    @PostMapping("/train")
    public Result train(@RequestBody TrainDTO request){
        logger.info("请求模型训练"+ request);
        if(aiService.Train(request)){
            return Result.success();
        }else return Result.error("样本集数量不够！");
    }

}
