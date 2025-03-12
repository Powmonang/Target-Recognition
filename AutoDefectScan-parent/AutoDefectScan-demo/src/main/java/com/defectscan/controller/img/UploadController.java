package com.defectscan.controller.img;

import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.Img;
import com.defectscan.result.Result;
import com.defectscan.service.ImgService;
import com.defectscan.tools.AliOssTool;
import com.defectscan.tools.UploadImgTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.io.Files.getFileExtension;


@RestController
@RequestMapping("/api/images/upload")
@CrossOrigin("*") // 允许跨域
@Api(tags = "用户上传图片接口")
@Slf4j
public class UploadController {

    // 获取当前工作目录（应用程序根目录）
    private final String currentDir = System.getProperty("user.dir");


    @Value("${demo.controller.img.tempDir}")
    private String tempDir;

    @Value("${demo.controller.img.targetDir}")
    private String targetDir;

    @Autowired
    private AliOssTool aliOssTool;

    @Autowired
    ImgService imgService;

    @Autowired
    UploadImgTool uploadImgTool;


    /**
     *
     * @param images
     * @return
     * @throws IOException
     */
    //@Log
    @PostMapping("/images_local")
    @ApiOperation("上传图片(本地存储)")
    public Result<List<String>> uploadImages_local(@RequestParam("images") List<MultipartFile> images) throws IOException {
        log.info("开始上传图片(本地), 上传的图片数量: {}", images.size());
        if (images == null || images.isEmpty()) {
            log.info("图像上传为空");
            return Result.error("图像上传为空");
        } else {
            List<String> uploadedFilePaths = new ArrayList<>();
            // 遍历所有上传的图片
            for (MultipartFile image : images) {
                try {
                    // 将图片存入本地临时文件夹
                    Path filePath = uploadImgTool.uploadImgToTemp(image, tempDir);
                    log.info("图片上传本地成功，临时存储地址为：{}", filePath);
                    uploadedFilePaths.add(filePath.toString());
                } catch (Exception e) {
                    log.info("图片上传失败，{}", e.toString());
                    return Result.error("图片上传失败：" + e.toString());
                }
            }
            // 返回所有上传文件的路径
            return Result.success(uploadedFilePaths);
        }
    }

    //@Log
    @PostMapping("/sureUpload")
    public Result sureUpLoad(@RequestBody UrlRequestDTO request) throws IOException {
        for (int i = 0; i < request.getUrl().size(); i++) {
            String tempUrl = request.getUrl().get(i);
            log.info("确认上传图片:{}" ,tempUrl);
            // 定义源图片
            Img image = new Img();
            try {
                //将临时文件夹图片截取至本地文件夹
                String imgTargetUrl = uploadImgTool.uploadImgToLocal(tempUrl,tempDir,targetDir);
                // 使用 Paths.get() 获取 Path 对象
                Path path = Paths.get(imgTargetUrl);
                // 获取文件名（包含扩展名）
                String fileName = path.getFileName().toString();


                // 设置图片名称、本地路径
                image.setImageName(fileName);
                image.setOriginalLocalUrl(imgTargetUrl);

                // 批量写入数据库
                imgService.addImg(image);
                request.getUrl().set(i, imgTargetUrl);
            } catch (IOException e) {
                log.error("上传失败: " + e.toString());
                return Result.error("上传失败：" + e.toString());
            }
        }
        log.info("所有图片上传成功,新地址为：{}",request.getUrl());
        return Result.success(request.getUrl());
    }




    // 图片批量上传接口(云服务器存储)
    //@Log
    @PostMapping("/images_aliyun")
    @ApiOperation("批量上传图片(云服务器存储)")
    public Result<List<String>> uploadImages_aliyun(@RequestParam("images") List<MultipartFile> images) throws IOException {
        log.info("开始批量上传图片(云服务器), 图片数量: {}", images.size());
        List<String> urls = new ArrayList<>();
        try {
            // 遍历上传每个图片
            for (MultipartFile image : images) {
                //先上传临时文件夹
                Path filePath = uploadImgTool.uploadImgToTemp(image, tempDir);
                //截取至本地文件夹
                String imgTargetUrl = uploadImgTool.uploadImgToLocal(filePath.toString(),tempDir,targetDir);

                // 使用 Paths.get() 获取 Path 对象
                Path path = Paths.get(imgTargetUrl);
                // 获取文件名（包含扩展名）
                String fileName = path.getFileName().toString();
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
                img.setOriginalLocalUrl(imgTargetUrl);
                img.setOriginalAliyunUrl(url);
                img.setImageName(fileName);
                imgService.addImg(img);  // 批量写入数据库
            }
            return Result.success(urls);
        } catch (IOException e) {
            log.error("文件上传失败:{}",e.toString());
            return Result.error(e.getMessage());
        }
    }




}
