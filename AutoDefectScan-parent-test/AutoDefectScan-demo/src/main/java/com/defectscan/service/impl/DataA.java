package com.defectscan.service.impl;

import com.defectscan.entity.*;
import com.defectscan.dto.GetGasDataDTO;
import com.defectscan.respond.GasRespond;
import com.defectscan.respond.GasRespondAll;
import com.defectscan.service.DataService;
import com.defectscan.service.ImgService;
import com.defectscan.service.ImgTypeService;
import com.defectscan.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataA implements DataService {
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.service.data.DataA");
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gas.token.grantType}")
    private String grantType;
    @Value("${gas.token.scope}")
    private String scope;

    @Value("${gas.token.clientId2}")
    private String clientId;
    @Value("${gas.token.clientSecret2}")
    private String clientSecret;
    @Value("${gas.getTokenURL2}")
    private String getTokenURL;
    @Value("${gas.getDataByIdURL2}")
    private String getDataByIdURL;

    @Value("${gas.getDataURL2}")
    private String getDataURL;

    @Value("${server.port}")
    private String port;

    @Value("${gas.idsTxt}")
    private String idsTxt;

    @Value("${gas.idsRestTxt}")
    private String idsRestTxt;

    @Value("${upload.officeDir}")
    private String officeDir;

    @Autowired
    private ImgService imgService;

    @Autowired
    private ImgTypeService imgTypeService;

    @Autowired
    private FileTool fileTool;


    // 获取token
    @Override
    public String getToken(){
        GasTokenBody t = new GasTokenBody(clientId, clientSecret, grantType, scope);
        HttpEntity<GasTokenBody> request = new HttpEntity<>(t);
        Result result = restTemplate.postForObject(getTokenURL, request, Result.class);
        Map data = (Map) result.getData();
        String Token = data.get("accessToken").toString();
        return Token;
    }

    // 把远程仓库的所有报告id存到本地
    @Override
    public Result getGasReportIdToFile() {
        logger.info("把远程仓库的所有报告id存到本地");
        List<String> reportIds = new ArrayList<>();
        //获取token
        String Token = getToken();
        // 2. GET请求所有的安检报告ID
        // 2.1 组装URL
        String IdURL = getDataURL;
        // 2.2 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // 如果API需要JSON格式的内容类型，即使对于GET请求也设置它（通常GET请求不需要）
        headers.setBearerAuth(Token); // 使用setBearerAuth方法添加Bearer Token
        // 2.3 创建HttpEntity（对于GET请求，body是null）
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // 2.4 发送GET请求
        GasRespondAll result1 = restTemplate.exchange(IdURL, HttpMethod.GET, requestEntity, GasRespondAll.class).getBody();

        //2.5获取到检查项图片 和 隐患集合
        List<GasReport> reports = result1.getData();
        for(GasReport i:reports){
            reportIds.add(i.getId());
        }
        //3. 将ID保存到txt文件中
        fileTool.writeLines(idsTxt, reportIds);
        //4. 还需要把未在本地的ID存到一个txt文件
        //4.1 查询数据库中已有的报告ID
        List<String> localIds = imgService.findReport(null);
        Set<String> local = new HashSet<>(localIds);
        Set<String> all = new HashSet<>(reportIds);
        all.removeAll(local);
        List<String> rest = new ArrayList<>(all);
        fileTool.writeLines(idsRestTxt, rest);
        return Result.success(reports.size());
    }

    /**
     * 根据TXT文件里面的reportID获取安检报告并下载到本地
     * @return null
     */
    @Override
    public Result getGasData(GetGasDataDTO rqt) {
        // 获取token
        String Token = getToken();
        //补充：从txt文件中获取ID
        List<String> reportIds = fileTool.readLines(idsRestTxt);
        //如果没有这个文件，就重新获取
        if(reportIds==null){
            getGasReportIdToFile();
        }
        // 用于统计保存了多少安检报告到本地
        long countSave = 0;
        // 用于保存这次请求在本地存了哪些安检报告
        List<String> saved = new ArrayList<>();
        for(String IdUrl: reportIds){
            logger.info("获取安检报告："+IdUrl);
            //首先要查询一下这个安检报告是不是已经在数据库里了
            Img imgTemp2 = new Img();
            imgTemp2.setReportId(IdUrl);
            List<Img> imgList2 = imgService.findImgs(imgTemp2);
            // 如果这个图片列表不是空的，就跳过这个报告
            if(!imgList2.isEmpty()){
                logger.info(IdUrl + "该安检报告已存在，跳过");
                continue;
            }
            // 2. 根据ID获取详情安检报告
            // 2.1 组装URL
            String IdURL = getDataByIdURL+IdUrl;
            // 2.2 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON); // 如果API需要JSON格式的内容类型，即使对于GET请求也设置它（通常GET请求不需要）
            headers.setBearerAuth(Token); // 使用setBearerAuth方法添加Bearer Token
            // 2.3 创建HttpEntity（对于GET请求，body是null）
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

            // 2.4 发送GET请求
            GasRespond result1 = restTemplate.exchange(IdURL, HttpMethod.GET, requestEntity, GasRespond.class).getBody();

            //2.5获取到检查项图片 和 隐患集合
            List<CheckPhotoReport> checkPhotoReports = result1.getData().getCheckPhotoReports();
            List<Hidden> hiddens = result1.getData().getHiddens();

            //2.6 将图片下载到本地指定目录
            Img imgTemp = new Img();
            PageBean imgResult = imgService.findImgsPage(imgTemp,1, 10);
            // 获取图片总数
            Long index = imgResult.getTotal();
            for(CheckPhotoReport i:checkPhotoReports){
                //获取图片URL
                String imgUrl = i.getUrl();
                // 如果这个图片地址为空，就跳过
                if(imgUrl == null||imgUrl.isEmpty()){
                    continue;
                }
                //生成图片本地地址
                String imgDownUrl = Paths.get(officeDir, index + 1 +".jpg").toString();
                //获取安检报告ID
                String reportId = i.getCheckReportId();
                //下载图片
                downloadImage(imgUrl, imgDownUrl);
                Img addImgRow = new Img();
                addImgRow.setId(String.valueOf(index+1));
                addImgRow.setUrl("/uploadReturn/"+ (index + 1) +".jpg");
                addImgRow.setUploader("一点淘气");
                addImgRow.setUploadTime(LocalDateTime.now().toString());
                addImgRow.setIsOpe("0");
                addImgRow.setShow("1");
                addImgRow.setSafeCount("-1");
                addImgRow.setReportId(reportId);
                index++;
                //查询一下这个图像内容类别type 有没有 没有就 加进去             addImgRow.setPhotoTagId(i.getPhotoTagId());
                ImgType imgType = new ImgType();
                imgType.setType(i.getPhotoTag());
                List<ImgType> imgTypes = imgTypeService.findImgType(imgType);
                if( imgTypes == null || imgTypes.isEmpty() )
                {
                    //获取当前的ID数量
                    long imgTypeCount  = imgTypeService.findImgType(ImgType.createById(null)).size();
                    imgType.setId(String.valueOf((imgTypeCount+1)));
                    imgTypeService.addImgType(imgType);
                    //增加这个类型同时把图片的TagId设置为它
                    addImgRow.setPhotoTagId(String.valueOf((imgTypeCount+1)));
                }else{
                    //如果存在该类型 把ID值赋予给图片
                    ImgType oneRow = imgTypes.get(0);
                    addImgRow.setPhotoTagId(oneRow.getId());
                }
                if(hiddens == null || hiddens.isEmpty()){
                    addImgRow.setMark("无隐患");
                }else{
                    StringBuilder markTemp = new StringBuilder();
                    for(Hidden j:hiddens){
                        markTemp.append(j.getName()).append("%");
                    }
                    addImgRow.setMark(markTemp.toString());
                }
//                System.out.println(addImgRow.toString());
                imgService.addImg(addImgRow);
            }
            countSave++;
            saved.add(IdUrl);
            // 如果获取了足够的报告到本地，就结束
            if(countSave>=rqt.getLimit()){
                break;
            }
        }
        Set<String> savedSet = new HashSet<>(saved);
        Set<String> all = new HashSet<>(reportIds);
        all.removeAll(savedSet);
        List<String> rest = new ArrayList<>(all);
        fileTool.writeLines(idsRestTxt, rest);
        return Result.success("共采集"+ countSave+"条报告到本地");
    }


    public void downloadImage(String imgUrl, String destFilePath) {
        try {
            // 发送 GET 请求并获取响应
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                    imgUrl, HttpMethod.GET, null, byte[].class);

            byte[] imageBytes = responseEntity.getBody();

            // 使用 Java NIO 写入文件（推荐方式）
            Path path = Paths.get(destFilePath);
            if (imageBytes != null) {
                Files.write(path, imageBytes);
            }

            System.out.println("图片下载成功：" + destFilePath);
        } catch (IOException e) {
            System.out.println("图片下载失败：");
        }
        // 注意：这里不需要关闭 ResponseEntity，因为它不包含需要关闭的资源
    }
}
