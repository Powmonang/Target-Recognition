package com.defectscan.service.impl;

import com.defectscan.constant.BaseContext;
import com.defectscan.constant.DefectType;
import com.defectscan.dto.AiDetectDTO;
import com.defectscan.vo.AiDetectReturnVO;
import com.defectscan.vo.ImgPageQueryVO;
import com.defectscan.entity.DefectInfo;
import com.defectscan.entity.Img;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.mapper.ImgDefectMapMapper;
import com.defectscan.mapper.ImgMapper;
import com.defectscan.result.PageResult;
import com.defectscan.service.ImgService;
import com.defectscan.tools.AliOssTool;
import com.defectscan.tools.ImgTool;
import com.defectscan.vo.ReturnPageImgVO;
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
import java.util.*;
import java.util.stream.Collectors;
import static com.defectscan.constant.DefectType.getDefectTypeMap;

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
    ImgDefectMapMapper imgDefectMapMapper;

    @Autowired
    ImgTool imgTool;

    @Autowired
    AliOssTool aliOssTool;

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
        a.setIsDetect("0");
        a.setIsOpe("0");
        a.setIsShow("0");
        a.setIsBackup("0");
        imgMapper.addImg(a);
    }

    /**
     * 调用ai进行图片检测
     * @param request
     * @return
     */
    @Override
    public ReturnDetectData detectImages(List<String> request) {

        // 定义识别后的图片
        List<AiDetectDTO> imgList = new ArrayList<>();
        for (int i = 0; i < request.size(); i++) {
            // 根据url从数据库中获取到对应图片的数据
            AiDetectDTO aiDetectDto = imgMapper.selectAiDetectDTOByUrl(request.get(i));
            imgList.add(aiDetectDto);
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
                for(AiDetectReturnVO aiDetectReturnDTO : returnDetectData.getDetectImgList()) {
                    Img img = new Img();
                    // 创建ai检测信息，更新对应的img
                    img.setId(aiDetectReturnDTO.getId());

                    img.setUpdateUser(BaseContext.getCurrentUsername());
                    img.setUpdateTime(LocalDateTime.now());

                    img.setDetectTime(aiDetectReturnDTO.getDefectTime());
                    img.setDefectCount(aiDetectReturnDTO.getDefectCount());
                    img.setIsDetect("1");

                    // 添加缺陷信息
                    if(aiDetectReturnDTO.getDefectCount() > 0)
                    {
                        // 记录缺陷类型
                        List<Integer> typeIdList = new ArrayList<>();
                        for(DefectInfo defectInfo : aiDetectReturnDTO.getDefectList()) {
                            if(!typeIdList.contains(defectInfo.getDefectId()))
                            {
                                typeIdList.add(defectInfo.getDefectId());
                            }
                            // 设置缺陷名称
                            defectInfo.setDefectName(DefectType.getDefectTypeMap().get(defectInfo.getDefectId()));
                            // 添加缺陷信息
                            imgDefectMapMapper.addDefectInfo(defectInfo);
                        }
                        String defectTypeId = typeIdList.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(","));
                        img.setDefectTypeId(defectTypeId);
                    }
                    // 更新对应图片信息
                    imgMapper.updateImgDetect(img);
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
    public PageResult pageQuery(int page, int pageSize, ImgPageQueryVO imgPageQueryDTO) {
        // 如果查询缺陷类型，则转换为对应的数字
        if(!imgPageQueryDTO.getDefectType().isEmpty())
        {
            for (Map.Entry<Integer, String> entry : getDefectTypeMap().entrySet()) {
                if (entry.getValue().equals(imgPageQueryDTO.getDefectType())) {
                    imgPageQueryDTO.setDefectType(entry.getKey().toString());
                }
            }
        }
        // 设置查询只能为当前用户
        if(imgPageQueryDTO.getCreateUser() == null)
        {
            imgPageQueryDTO.setCreateUser(BaseContext.getCurrentUsername());
        }
        // 查询总记录数
        long totalRecords  = imgMapper.pageSum(imgPageQueryDTO);
        // 计算总页数
        int totalPages = (int) ((totalRecords + pageSize - 1) / pageSize);

        // 将当前页码转换为当前页码第一条记录的位置
        int startIndex = (page - 1) * pageSize;

        List<ReturnPageImgVO> result = imgMapper.pageQuery(startIndex, pageSize, imgPageQueryDTO);
        for (ReturnPageImgVO img : result) {
            List<DefectInfo> defectInfoList = imgDefectMapMapper.getDefectInfoByImgId(img.getId());
            if(defectInfoList != null && defectInfoList.size() > 0) {
                for (DefectInfo defectInfo : defectInfoList) {
                    String defectName = defectInfo.getDefectName();
                    // 如果 defectName 对应的 List 不存在，则创建一个新的 List
                    img.getDefectMap().computeIfAbsent(defectName, k -> new ArrayList<>()).add(defectInfo);
                }
                // 将字符串按逗号分割并转换为整数数组
                String str = img.getDefectType();
                int[] intArray = Arrays.stream(str.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                // 将缺陷类型id转换为对应的缺陷类型
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < intArray.length; i++) {
                    int id = intArray[i];
                    String defectType = getDefectTypeMap().get(id);
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(defectType);

                }
                img.setDefectType(sb.toString());
            }

        }
        return new PageResult(totalPages, totalRecords, result);
    }


    @Override
    public boolean updateImg(int id, String mark) {
        Img img = imgMapper.findImgById(Long.valueOf(id));
        img.setUpdateTime(LocalDateTime.now());
        img.setUpdateUser(BaseContext.getCurrentUsername());
        img.setMark(mark);
        imgMapper.updateImg(img);
        return true;
    }

    @Override
    public boolean deleteImg(Long id) {
        Img img = imgMapper.findImgById(id);
        if (img != null) {
            imgMapper.deleteImg(img);
            String localUrl = img.getLocalDir();
            String aliyunUrl = img.getAliyunUrl();
            if(localUrl != null){
                try {
                    imgTool.deleteLocalImg(localUrl);
                } catch (IOException e) {
                    log.info("删除错误：{}",localUrl);
                    throw new RuntimeException(e);
                }
            }
            if(aliyunUrl != null){
                try {
                    aliOssTool.deleteFile(aliyunUrl);
                } catch (Exception e) {
                    log.info("删除云端数据错误：{}",localUrl);
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }
}
