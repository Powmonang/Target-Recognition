package com.defectscan.service.impl;

import com.defectscan.constant.BaseContext;
import com.defectscan.dto.ImgPageQueryDTO;
import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.Img;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.mapper.ImgMapper;
import com.defectscan.result.PageResult;
import com.defectscan.service.ImgService;
import com.defectscan.tools.ImgTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ImgServiceImpl implements ImgService {

    @Value("${demo.service.ai.detect}")
    private String detectUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ImgMapper imgMapper;

    @Autowired
    ImgTool imgTool;

    /**
     * 添加（上传）图片
     * @param a
     */
    @Override
    public void addImg(Img a) {
        a.setCreateUser(BaseContext.getCurrentUsername());
        a.setUpdateUser(BaseContext.getCurrentUsername());
        a.setCreateTime(LocalDateTime.now());
        a.setUpdateTime(LocalDateTime.now());
        imgMapper.addImg(a);
    }

    /**
     * 调用ai进行图片检测
     * @param request
     * @return
     */
    @Override
    public ReturnDetectData detectImages(UrlRequestDTO request) {

        // 定义识别后的图片
        List<Img> imgList = new ArrayList<>();
        for (int i = 0; i < request.getUrl().size(); i++) {
            // 根据url从数据库中获取到对应图片的数据
            // 已有值：       id imageName originalLocalUrl (originalAliyunUrl) createTime updateTime createUser updateUser
            // 需返回更新值：  detectLocalUrl detectAliyunUrl detectTime defectTagId defectCount confidenceLevel isPpe
            Img findImg = imgMapper.selectImgByUrl(request.getUrl().get(i));
            imgList.add(findImg);
        }
        // 调用 AI 检测服务
        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);// 如果API需要JSON格式的内容类型，即使对于GET请求也设置它（通常GET请求不需要）
            // 请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("detectImgList", imgList);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            // 获取ai检测结果
            ReturnDetectData returnDetectData = restTemplate.exchange(detectUrl, HttpMethod.POST, requestEntity, ReturnDetectData.class).getBody();
            //ReturnDetectData ret_obj  = restTemplate.postForObject(detectUrl, requestEntity, ReturnDetectData.class);

            //将新地址写入检测后数据库
            if (returnDetectData != null) {
                // 检测完之后更新 updateTime  updateUser  isDetect
                for(int i = 0; i < returnDetectData.getDetectImgList().size(); i++) {
                    returnDetectData.getDetectImgList().get(i).setUpdateTime(LocalDateTime.now());  // 检测（更新）时间
                    returnDetectData.getDetectImgList().get(i).setUpdateUser(BaseContext.getCurrentUsername()); // 更新用户
                    returnDetectData.getDetectImgList().get(i).setIsDetect("1");    // 设置已更新
                    imgMapper.updateImgDetect(returnDetectData.getDetectImgList().get(i));
                }
            }
            // 返回处理后的数据
            return returnDetectData;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页查询图片
     * @param page 页码
     * @param pageSize 每页记录数
     * @param imgPageQueryDTO 查询条件
     * @return
     */
    @Override
    public PageResult pageQuery(int page, int pageSize, ImgPageQueryDTO imgPageQueryDTO) {
        //PageHelper.startPage(imgPageQueryDTO.getPage(),imgPageQueryDTO.getPageSize());
        // 查询总记录数
        long totalRecords  = imgMapper.pageSum(imgPageQueryDTO);
        // 计算总页数
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);

        // 将当前页码转换为当前页码第一条记录的位置
        int startIndex = (page - 1) * pageSize;

        List<Img> result = imgMapper.pageQuery(startIndex, pageSize, imgPageQueryDTO);
        return new PageResult(totalPages, totalRecords, result);
    }


    @Override
    public boolean updateImg(Img img) {
        img.setUpdateTime(LocalDateTime.now());
        img.setUpdateUser(BaseContext.getCurrentUsername());
        imgMapper.updateImg(img);
        return true;
    }

    @Override
    public boolean deleteImg(Long id) {
        Img img = imgMapper.findImgById(id);
        if (img != null) {
            imgMapper.deleteImg(img);
            String originalLocalUrl = img.getOriginalLocalUrl();
            String originalAliyunUrl = img.getOriginalAliyunUrl();
            String detectLocalUrl = img.getDetectLocalUrl();
            String detectAliyunUrl = img.getDetectAliyunUrl();
            if(originalLocalUrl != null){
                try {
                    imgTool.deleteLocalImg(originalLocalUrl);
                } catch (IOException e) {
                    log.info("删除错误：{}",originalLocalUrl);
                    throw new RuntimeException(e);
                }
            }
            // 删除云端源图片
            if(originalAliyunUrl != null){
                try {
                    imgTool.deleteLocalImg(originalAliyunUrl);
                } catch (IOException e) {
                    log.info("删除错误：{}",originalAliyunUrl);
                    throw new RuntimeException(e);
                }
            }
            // 删除本地检测图片
            if(detectLocalUrl != null){
                try {
                    imgTool.deleteLocalImg(detectLocalUrl);
                } catch (IOException e) {
                    log.info("删除错误：{}",detectLocalUrl);
                    throw new RuntimeException(e);
                }
            }
            // 删除云端检测图片
            if(detectAliyunUrl != null){
                try {
                    imgTool.deleteLocalImg(detectAliyunUrl);
                } catch (IOException e) {
                    log.info("删除错误：{}",detectAliyunUrl);
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
        return false;
    }
}
