package com.defectscan.controller.ai;


import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.Img;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.result.Result;
import com.defectscan.service.ImgService;
import com.defectscan.tools.AliOssTool;
import com.defectscan.tools.FileTool;
import com.defectscan.tools.ImgTool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.io.Files.getFileExtension;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin("*") // 允许跨域
@Api(tags = "与ai模型对接接口")
@Slf4j
public class ImgDetectController {

    @Autowired
    private ImgService imgService;

    @Autowired
    AliOssTool aliOssTool;

    @Autowired
    ImgTool uploadImgTool;

    @Value("${demo.controller.img.tempDir}")
    private String tempDir;

    @Value("${demo.controller.img.targetDir}")
    private String targetDir;

    @Autowired
    private FileTool fileTool;


    //@Log
    @PostMapping("/detect")
    public Result detect(@RequestParam("images") MultipartFile[] files){
        List<MultipartFile> images = new ArrayList<>();
        for (MultipartFile file : files) {
            if (fileTool.isCompressedFile(file.getOriginalFilename())) {
                List<MultipartFile> compressedImages = fileTool.processCompressedFile(file);
                images.addAll(compressedImages);
            } else {
                images.add(file);
            }
        }

        log.info("开始批量上传图片(云服务器), 图片数量: {}", images.size());

        List<String> urls = new ArrayList<>();
        try {
            // 遍历上传每个图片
            for (MultipartFile image : images) {
                //先上传临时文件夹
                Map.Entry<String, String> pair = uploadImgTool.uploadImgToTemp(image, tempDir);
                //截取至本地文件夹
                String imgTargetUrl = uploadImgTool.uploadImgToLocal(pair.getValue(),tempDir,targetDir);

                // 使用 Paths.get() 获取 Path 对象
                Path path = Paths.get(imgTargetUrl);
                // 获取文件名（包含扩展名）
                String fileName = path.getFileName().toString();
                // 获取原始文件名
                String originalFileName = pair.getKey();
                // 获取文件扩展名（从文件名中提取）
                String fileExtension = getFileExtension(fileName);
                // 获取文件所在的目录路径
                String fileDir = path.getParent().toString();

                String url = aliOssTool.upload(image.getBytes(), fileName);
                log.info("文件上传完成, 文件访问的 URL: {}", url);
                // 将上传的文件 URL 添加到返回列表中
                urls.add(url);

                // 将图片上传数据库
                // 定义源图片
                Img img = new Img();
                img.setLocalDir(imgTargetUrl);
                img.setAliyunUrl(url);
                img.setImageName(originalFileName);
                imgService.addImg(img);  // 批量写入数据库
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}",e.toString());
            return Result.error(e.getMessage());
        }


        log.info("请求模型推理");
        try {
            ReturnDetectData returnDetectData = imgService.detectImages(urls);
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
