package com.defectscan.service.impl;

import com.defectscan.entity.OpeImg;
import com.defectscan.entity.Result;
import com.defectscan.dto.TrainDTO;
import com.defectscan.request.TrainRequest;
import com.defectscan.mapper.OpeImgMapper;
import com.defectscan.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AIA implements AIService {

    @Autowired
    private OpeImgMapper opeImgMapper;

    @Value("${ai.train}")
    private String TrainURL;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean Train(TrainDTO trainDTO) {
        long trainSize = 10; //十张图片才能训
        Map<String, String> lrMap = new HashMap<>();
        lrMap.put("较小", "0.00001");
        lrMap.put("适中", "0.0001");
        lrMap.put("较大", "0.001");
        List<String> args = trainDTO.getArgs();
        Map<String, String> model=trainDTO.getModel();
        String epoch = args.get(0);
        String lr = args.get(1);
        boolean isPre = "0".equals(args.get(2)); // 如果arg等于"0"，则isPre为true，否则为false
        Map<String, Object> param = new HashMap<>();
        param.put("epoch", epoch);
        param.put("learningRate", lrMap.get(lr));
        param.put("isNewModel", isPre);
        for(String id:model.keySet()){
            // 图片集合
            List<String> imgIds = new ArrayList<>();
            //根据隐患ID 从样本集合里面获取图片集合
            OpeImg opeImg=new OpeImg();
            opeImg.setSafeType(id);
            List<OpeImg> opeImgList = opeImgMapper.findOpeImgs(opeImg);
            // 判断一下这个opeImgList是否足够多图片
            if(opeImgList.size()<trainSize){
                System.out.println("图片数量不足");
                return false;
            }
            for(OpeImg o:opeImgList){
                imgIds.add(o.getImgId());
            }
            System.out.println(imgIds);
            // 新建一个训练请求实体
            TrainRequest trainRequest = new TrainRequest();
            trainRequest.setBaseModel(model.get(id));
            trainRequest.setImageData(imgIds);
            trainRequest.setTrainParameter(param);
            HttpEntity<TrainRequest> request2 = new HttpEntity<>(trainRequest);
            System.out.println(restTemplate.postForObject(TrainURL, request2, Result.class));
        }
        return true;
    }
}
