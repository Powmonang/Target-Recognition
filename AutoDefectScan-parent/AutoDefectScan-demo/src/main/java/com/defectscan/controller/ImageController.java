package com.defectscan.controller;

import com.defectscan.result.Result;
import com.defectscan.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://120.24.30.217:8080")
@RequestMapping("/api/images")
@Api(tags = "用户上传图片接口")
@Slf4j
public class ImageController {

    // 图片保存路径
    private static final String IMAGE_UPLOAD_DIR = "/path/to/upload/directory/";

    @Autowired
    private AliOssUtil aliOssUtil;


    // 图片上传接口(本地存储)
    /**
     *
     * @param image
     * @return
     */

    @PostMapping("/upload_local")
    @ApiOperation("上传图片(本地存储)")
    public Result<String> uploadImage_local(MultipartFile image) throws IOException {
        log.info("开始上传图片(本地),文件名:{}", image.getOriginalFilename());
        return Result.success();
    }

    // 图片上传接口(云服务器存储)
    @PostMapping("/upload_aliyun")
    @ApiOperation("上传图片(云服务器存储)")
    public Result<String> uploadImage_aliyun(MultipartFile image) throws IOException {
        log.info("开始上传图片(云服务器),文件名:{}", image.getOriginalFilename());
        //调用阿里云OSS工具类进行文件上传
        try {
            //原始文件名
            String originalFilename = image.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            //文件的请求路径
            String url = aliOssUtil.upload(image.getBytes(), objectName);
            log.info("文件上传完成,文件访问的url: {}", url);
            return Result.success(url);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
            return Result.error(e.getMessage());
        }
    }

//    // 获取上传图片（示例）
//    @GetMapping("/{imageName}")
//    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
//        Path imagePath = Paths.get(IMAGE_UPLOAD_DIR + imageName);
//        if (Files.exists(imagePath)) {
//            byte[] imageBytes = Files.readAllBytes(imagePath);
//            return ResponseEntity.ok(imageBytes);
//        }
//        return ResponseEntity.notFound().build();
//    }
}
