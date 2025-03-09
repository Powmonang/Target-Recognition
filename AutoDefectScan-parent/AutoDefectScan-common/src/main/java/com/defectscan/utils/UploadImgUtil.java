package com.defectscan.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Slf4j
@Component
public class UploadImgUtil {

    // 获取当前工作目录（应用程序根目录）
    private final String currentDir = System.getProperty("user.dir");

    public Path uploadImgToTemp(MultipartFile image,String tempDir) throws IOException {
        String originalFileName = image.getOriginalFilename();
        if (originalFileName == null) {
            log.info("上传图像原始名称为空");
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
        // 将图片保存到指定目录
        //image.transferTo(new File(filePath.toString()));
        Files.copy(image.getInputStream(), filePath);
        return filePath;
    }


    public String uploadImgToLocal(String tempUrl,String tempDir,String targetDir) throws IOException {
        // 获取目标目录路径
        Path targetFileDir = Paths.get(currentDir, "data", targetDir);
        // 检查目标目录是否存在，不存在则创建
        if (!Files.exists(targetFileDir)) {
            Files.createDirectories(targetFileDir);
        }

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
            return targetPath.toString();
        } catch (IOException e) {
            log.error("上传失败: " + e.toString());
            return null;
        }
    }


}