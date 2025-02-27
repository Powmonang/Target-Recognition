package com.defectscan.service.impl;

import com.defectscan.dto.OriginAndDetect;
import com.defectscan.entity.ImgDetectData;
import com.defectscan.dto.ImgDetectListDTO;
import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.mapper.ImgDetectMapper;
import com.defectscan.result.Result;
import com.defectscan.service.ImgDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImgDetectServiceImpl implements ImgDetectService {

    @Value("${controller.ai.detect}")
    private String detectUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ImgDetectMapper imgDetectMapper;


    @Override
    public ReturnDetectData detectImages(UrlRequestDTO request) {

        // 定义识别后的图片
        List<ImgDetectData> imgDetectListDTO = new ArrayList<>();
        for (int i = 0; i < request.getUrl().size(); i++) {
            ImgDetectData dto = new ImgDetectData();
            // 根据url从数据库中获取到对应图片的id、localurl、aluyunurl、imgName
            OriginAndDetect originAndDetect = imgDetectMapper.selectOriginIdByUrl(request.getUrl().get(i));
            dto.setOriginalId(originAndDetect.getId());                 //设置所对应源图片id
            dto.setOriginalLocalUrl(originAndDetect.getLocalUrl());     //设置所以应的源文件本地位置
            dto.setOriginalAliyunUrl(originAndDetect.getAliyunUrl());   //设置源文件云端地址
            dto.setDefectImageName(originAndDetect.getImgName());       //设置识别后文件名称

            imgDetectListDTO.add(dto);
        }
        // 调用 AI 检测服务
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);// 如果API需要JSON格式的内容类型，即使对于GET请求也设置它（通常GET请求不需要）
            // 请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("imgDetectListDTO", imgDetectListDTO);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            // 获取ai检测结果
            ReturnDetectData returnDetectData = restTemplate.exchange(detectUrl, HttpMethod.POST, requestEntity, ReturnDetectData.class).getBody();
            //ReturnDetectData ret_obj  = restTemplate.postForObject(detectUrl, requestEntity, ReturnDetectData.class);

            //将新地址写入检测后数据库
            if (returnDetectData != null) {
                imgDetectMapper.insertImgDetectList(returnDetectData.getImgDetectListDTO());
            }
            // 返回处理后的数据
            return returnDetectData;
        }catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
