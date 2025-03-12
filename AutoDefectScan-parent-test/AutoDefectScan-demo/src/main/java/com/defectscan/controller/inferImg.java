package com.defectscan.controller;

import com.defectscan.entity.Img;
import com.defectscan.entity.InferImg;
import com.defectscan.entity.PageBean;
import com.defectscan.entity.Result;
import com.defectscan.service.ImgService;
import com.defectscan.service.InferImgService;
import com.defectscan.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
@RequestMapping("/api/img")
public class inferImg {
    @Autowired
    InferImgService inferImgService;

    @Autowired
    ImgService imgService;

    @Value("${inferIndexDir}")
    private String inferIndexDir;

    @Autowired
    FileTool fileTool;

    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.inferImg");

    @PostMapping("/addInfer")
    public Result addInfer(@RequestBody InferImg request){
        logger.info("推理图片隐患："+request.toString());
        if(request.getReportId() == null || request.getReportId().isEmpty()) {
            return Result.error("未写入安检报告ID！");
        }

        if(request.getIndexUrl().equals("nullPoint")){
            logger.info("无检测结果");
            return Result.error("无检测结果");
        }
        // 生成文件名
        String indexUrlName = "Infer"+"_"+request.getOper() + "_" + request.getImgId() + "_" + request.getSafeType() + ".txt";
        // 生成坐标文件地址
        String indexUrl = Paths.get(inferIndexDir, indexUrlName).toString();
        //先用操作者ID + 图片ID + 隐患ID 查询一下有没有这个记录
        InferImg Temp = new InferImg();
        Temp.setOper(request.getOper());
        Temp.setImgId(request.getImgId());
        Temp.setSafeType(request.getSafeType());
        List<InferImg> resultTemp = inferImgService.findInfers(Temp);
        //先把坐标写入坐标文件中
        fileTool.writeLine(indexUrl, request.getIndexUrl());
        //更新request的indexUrl 换成真的indexUrl
        request.setIndexUrl(indexUrl);
        //获取当前时间戳
        request.setKeyTime(LocalDateTime.now().toString());
        if(resultTemp.isEmpty()){
            logger.info("新增推理记录:"+ request);
            //如果没有这个记录 就新增一个记录
            inferImgService.addInfer(request);
            //同时把img里面的safeCount 增加1
            Img imgTemp = new Img();
            imgTemp.setId(request.getImgId());
            // 查询当前的safeCount
            PageBean imgResultTemp = imgService.findImgsPage(imgTemp,1, 5);
            Img temp2 = (Img) imgResultTemp.getRows().get(0);
            String nowCount = temp2.getSafeCount();
            if(nowCount.equals("未诊断")){
                nowCount = "1";
            }else{
                nowCount = String.valueOf(Integer.parseInt(nowCount) + 1);
            }
            temp2.setSafeCount(nowCount);
            // 更新
            logger.info("更新图像推理记录"+ temp2);
            imgService.changeImg(temp2);
        }else
        {
            logger.info("修改推理记录:"+ request);
            inferImgService.changeInfer(request);
        }
        return Result.success();
    }


    @PostMapping("/findInfer")
    public Result findInfer(@RequestBody InferImg request){
        logger.info("查询推理记录："+ request.toString());
        return Result.success(inferImgService.findInfers(request));
    }
}
