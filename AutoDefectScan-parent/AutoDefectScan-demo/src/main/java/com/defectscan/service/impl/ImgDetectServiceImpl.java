package com.defectscan.service.impl;

import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.Img;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.mapper.ImgDetectMapper;
import com.defectscan.service.ImgDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImgDetectServiceImpl implements ImgDetectService {

    @Value("${demo.service.ai.detect}")
    private String detectUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ImgDetectMapper imgDetectMapper;


    @Override
    public ReturnDetectData detectImages(UrlRequestDTO request) {

        // 定义识别后的图片
        List<Img> imgList = new ArrayList<>();
        for (int i = 0; i < request.getUrl().size(); i++) {
            // 根据url从数据库中获取到对应图片的数据
            // 已有值：       id imageName originalLocalUrl (originalAliyunUrl) createTime updateTime createUser updateUser
            // 需返回更新值：  detectLocalUrl detectAliyunUrl detectTime defectTagId defectCount confidenceLevel isPpe
            Img findImg = imgDetectMapper.selectImgByUrl(request.getUrl().get(i));
            imgList.add(findImg);
        }
        // 调用 AI 检测服务
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);// 如果API需要JSON格式的内容类型，即使对于GET请求也设置它（通常GET请求不需要）
            // 请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("imgDetectListDTO", imgList);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            // 获取ai检测结果
            ReturnDetectData returnDetectData = restTemplate.exchange(detectUrl, HttpMethod.POST, requestEntity, ReturnDetectData.class).getBody();
            //ReturnDetectData ret_obj  = restTemplate.postForObject(detectUrl, requestEntity, ReturnDetectData.class);

            //将新地址写入检测后数据库
            if (returnDetectData != null) {
                // 检测完之后更新 updateTime  updateUser  isOpe
                for(int i = 0; i < returnDetectData.getDetectImgList().size(); i++) {
                    returnDetectData.getDetectImgList().get(i).setUpdateTime(LocalDateTime.now().toString());
                    //returnDetectData.getDetectImgList().get(i).setUpdateUser();
                    returnDetectData.getDetectImgList().get(i).setIsOpe(1);
                }
                imgDetectMapper.updateImgDetectList(returnDetectData.getDetectImgList());
            }
            // 返回处理后的数据
            return returnDetectData;
        }catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
