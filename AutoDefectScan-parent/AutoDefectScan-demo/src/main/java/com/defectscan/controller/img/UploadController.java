package com.defectscan.controller.img;

import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.ImgOrigin;
import com.defectscan.result.Result;
import com.defectscan.service.ImgOriginService;
import com.defectscan.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/images/upload")
@CrossOrigin("*") // 允许跨域
@Api(tags = "用户上传图片接口")
@Slf4j
public class UploadController {

    // 获取当前工作目录（应用程序根目录）
    private final String currentDir = System.getProperty("user.dir");


    @Value("${controller.img.tempDir}")
    private String tempDir;

    @Value("${controller.img.targetDir}")
    private String targetDir;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    ImgOriginService imgOriginService;



    // 图片上传接口(本地存储)

    /**
     *
     * @param images
     * @return
     * @throws IOException
     */
    @PostMapping("/images_local")
    @ApiOperation("上传图片(本地存储)")
    public Result<List<String>> uploadImages_local(@RequestParam("images") List<MultipartFile> images) throws IOException {
        log.info("开始上传图片(本地), 上传的图片数量: {}", images.size());

        if (images != null && !images.isEmpty()) {
            List<String> uploadedFilePaths = new ArrayList<>();

            // 遍历所有上传的图片
            for (MultipartFile image : images) {
                String originalFileName = image.getOriginalFilename();
                if (originalFileName == null) {
                    log.info("上传图像原始名称为空");
                    return Result.error("上传图像原始名称为空");
                }

                // 获取当前图片扩展名
                int index = originalFileName.lastIndexOf(".");
                String extname = originalFileName.substring(index);

                // 生成新的文件名（唯一）
                String newFileName = UUID.randomUUID() + extname;

                // 拼接完整的目标路径
                Path tempFileDir = Paths.get(currentDir, "data", tempDir);

                // 检查目标目录是否存在，不存在则创建
                if (!Files.exists(tempFileDir)) {
                    Files.createDirectories(tempFileDir);
                }

                // 拼接相对路径和新文件名
                Path filePath = Paths.get(tempFileDir.toString(), newFileName); // 使用当前目录存储

                try {
                    // 将图片保存到指定目录
                    image.transferTo(new File(filePath.toString()));
                    log.info("图片上传本地成功，临时存储地址为：{}", filePath);
                    uploadedFilePaths.add(filePath.toString());
                } catch (Exception e) {
                    log.info("图片上传失败，{}", e.toString());
                    return Result.error("图片上传失败：" + e.toString());
                }
            }

            // 返回所有上传文件的路径
            return Result.success(uploadedFilePaths);
        } else {
            log.info("图像上传为空");
            return Result.error("图像上传为空");
        }
    }

    @PostMapping("/sureUpload")
    public Result sureUpLoad(@RequestBody UrlRequestDTO request) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        // 获取目标目录路径
        Path targetFileDir = Paths.get(currentDir, "data", targetDir);

        // 检查目标目录是否存在，不存在则创建
        if (!Files.exists(targetFileDir)) {
            Files.createDirectories(targetFileDir);
        }

        for (int i = 0; i < request.getUrl().size(); i++) {
            String tempUrl = request.getUrl().get(i);
            log.info("确认上传图片:{}" ,tempUrl);
            // 定义源图片
            ImgOrigin image = new ImgOrigin();
            image.setCreateTime(now.toString());// 为每张图片设置上传时间
            // 将当前图片的地址改为目标存储地址
            String imgTargetUrl = tempUrl.replace(tempDir, targetDir);

            // 获取临时地址和目标地址的绝对路径
            Path sourcePath = Paths.get(tempUrl);
            Path targetPath = Paths.get(imgTargetUrl);

            try {
                // 使用Files.copy方法复制文件，并指定REPLACE_EXISTING选项以替换目标位置已存在的文件
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                // 删除源文件
                Files.delete(sourcePath);
                log.info("文件已成功剪切到：" + targetPath);

                // 更新图片的URL
                image.setLocalUrl(imgTargetUrl);
                imgOriginService.addImgOrigin(image);  // 批量写入数据库
                request.getUrl().set(i, imgTargetUrl);
            } catch (IOException e) {
                log.error("上传失败: " + e.toString());
                return Result.error("上传失败：" + e.toString());
            }
        }

        return Result.success("所有图片上传成功,新地址为：" + request.getUrl().toString());
    }






    // 图片批量上传接口(云服务器存储)
    @PostMapping("/images_aliyun")
    @ApiOperation("批量上传图片(云服务器存储)")
    public Result<List<String>> uploadImages_aliyun(@RequestParam("images") List<MultipartFile> images) throws IOException {
        log.info("开始批量上传图片(云服务器), 图片数量: {}", images.size());

        List<String> urls = new ArrayList<>();

        try {
            // 遍历上传每个图片
            for (MultipartFile image : images) {
                // 原始文件名
                String originalFilename = image.getOriginalFilename();
                // 截取原始文件名的后缀
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                // 构造新文件名称
                String objectName = UUID.randomUUID() + extension;
                // 文件的请求路径
                String url = aliOssUtil.upload(image.getBytes(), objectName);
                log.info("文件上传完成, 文件访问的 URL: {}", url);
                // 将上传的文件 URL 添加到返回列表中
                urls.add(url);
            }
            return Result.success(urls);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
            return Result.error(e.getMessage());
        }
    }




}
