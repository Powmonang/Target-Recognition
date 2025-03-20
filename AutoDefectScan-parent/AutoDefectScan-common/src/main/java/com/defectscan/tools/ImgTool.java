package com.defectscan.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Slf4j
@Component
public class ImgTool {

    // 获取当前工作目录（应用程序根目录）
    private final String currentDir = System.getProperty("user.dir");

    @Autowired
    AliOssTool aliOssTool;

    /**
     * 将图片存入临时目录
     * @param image
     * @param tempDir
     * @return
     * @throws IOException
     */
    public Map.Entry<String, Path> uploadImgToTemp(MultipartFile image,String tempDir) throws IOException {
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
        Files.copy(image.getInputStream(), filePath);
        Map.Entry<String, Path> pair = new AbstractMap.SimpleEntry<>(originalFileName, filePath);
        return pair;
    }


    /**
     * 将临时图片正式存入本地
     * @param tempUrl
     * @param tempDir
     * @param targetDir
     * @return
     * @throws IOException
     */
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


    public boolean deleteLocalImg(String localDir) throws IOException {
        // 删除本地文件
        File localFile = new File(localDir);
        if (localFile.exists()) {
            localFile.delete();
            log.info("已找到文件：{}，删除成功", localFile);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除云端图片
     * @param aliyunUrl
     * @return
     * @throws IOException
     */
    public boolean deleteAliyunImg(String aliyunUrl) throws IOException {
        // 使用 Paths.get() 获取 Path 对象
        Path path = Paths.get(aliyunUrl);
        // 获取文件名（包含扩展名）
        String objectName = path.getFileName().toString();
        if(aliOssTool.deleteFile(objectName)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 将本地图片备份到本地
     * @param originalLocalUrl
     * @param detectLocalUrl
     * @return
     * @throws IOException
     */
    public List<String> backupImgToLocal(String originalLocalUrl,String detectLocalUrl) throws IOException {
        // 获取备份目标目录路径currentDir/backup/original
        Path backupOriginalTargetFileDir = Paths.get(currentDir, "backup","original");
        // 获取备份目标目录路径currentDir/backup/detect
        Path backupDetectTargetFileDir = Paths.get(currentDir, "backup", "detect");
        // 检查目标目录是否存在，不存在则创建
        if (!Files.exists(backupOriginalTargetFileDir)) {
            Files.createDirectories(backupOriginalTargetFileDir);
        }
        if (!Files.exists(backupDetectTargetFileDir)) {
            Files.createDirectories(backupDetectTargetFileDir);
        }
        try {
            // 用于存储备份后的图片路径
            List<String> backupList = new ArrayList<>();

            // 获取源图片的path值
            Path originalLocalPath = Paths.get(originalLocalUrl);
            // 获取源图片唯一文件名
            String originalFileName = originalLocalPath.getFileName().toString();
            // 拼接完整备份文件路径（目标路径+文件名）
            Path originalBackupLocalUrl = Paths.get(backupOriginalTargetFileDir.toString(), originalFileName);
            // 使用Files.copy方法复制文件，并指定REPLACE_EXISTING选项以替换目标位置已存在的文件
            Files.copy(originalLocalPath, originalBackupLocalUrl, StandardCopyOption.REPLACE_EXISTING);
            backupList.add(originalBackupLocalUrl.toString());

            // 如果图片已检测
            if(detectLocalUrl != null && !detectLocalUrl.isEmpty()){
                // 获取源图片的path值
                Path detectLocalPath = Paths.get(detectLocalUrl);
                // 获取源图片唯一文件名
                String detectFileName = detectLocalPath.getFileName().toString();
                // 拼接完整备份文件路径（目标路径+文件名）
                Path detectBackupLocalUrl = Paths.get(backupDetectTargetFileDir.toString(), detectFileName);
                // 使用Files.copy方法复制文件，并指定REPLACE_EXISTING选项以替换目标位置已存在的文件
                Files.copy(detectLocalPath, detectBackupLocalUrl, StandardCopyOption.REPLACE_EXISTING);
                backupList.add(detectBackupLocalUrl.toString());
            }

            log.info("文件已成功备份到：{}", backupList);
            return backupList;
        } catch (IOException e) {
            log.error("备份失败: " + e);
            return null;
        }
    }


    /**
     * 将本地图片备份到云端
     * @param originalLocalUrl
     * @param detectLocalUrl
     * @return
     * @throws IOException
     */
    public List<String> backupImgToAliyun(String originalLocalUrl,String detectLocalUrl) throws IOException {

        try {
            // 用于存储备份后的图片路径
            List<String> backupList = new ArrayList<>();

            // 将当前源图片上传阿里云
            File originalFile = new File(originalLocalUrl);
            byte[] originalBytes = new byte[(int) originalFile.length()];
            try (InputStream inputStream = new FileInputStream(originalFile)) {
                inputStream.read(originalBytes);
            }
            String fileName = originalFile.getName();
            backupList.add(aliOssTool.upload(originalBytes, fileName));

            // 如果图片已检测
            if(detectLocalUrl != null && !detectLocalUrl.isEmpty()){
                File detectFile = new File(originalLocalUrl);
                byte[] detectBytes = new byte[(int) detectFile.length()];
                try (InputStream inputStream = new FileInputStream(detectFile)) {
                    inputStream.read(detectBytes);
                }
                String fileName2 = originalFile.getName();
                backupList.add(aliOssTool.upload(detectBytes, fileName2));
            }

            log.info("文件已成功备份到：{}", backupList);
            return backupList;
        } catch (IOException e) {
            log.error("备份失败: " + e);
            return null;
        }
    }

    public boolean restoreLocalImg(String originalLocalUrl, String originalBackupUrl,
                                        String detectLocalUrl, String detectBackupUrl) throws IOException {
        try {
            // 获取本地源路径 和 备份图片路径 path值
            Path originalLocalPath = Paths.get(originalLocalUrl);
            Path originalBackupPath = Paths.get(originalBackupUrl);
            // 获取去除文件名的目录路径
            Path originalLocalFileDir = originalLocalPath.getParent();
            Path originalBackupFileDir = originalBackupPath.getParent();
            // 检查目标目录是否存在，不存在则创建
            if (!Files.exists(originalLocalFileDir)) {
                Files.createDirectories(originalLocalFileDir);
            }
            if (!Files.exists(originalBackupFileDir)) {
                Files.createDirectories(originalBackupFileDir);
            }
            // 使用Files.copy方法复制文件，并指定REPLACE_EXISTING选项以替换目标位置已存在的文件
            Files.copy(originalBackupPath, originalLocalPath, StandardCopyOption.REPLACE_EXISTING);


            if(detectLocalUrl != null && !detectLocalUrl.isEmpty()){
                // 获取本地源路径 和 备份图片路径 path值
                Path detectLocalPath = Paths.get(detectLocalUrl);
                Path detectBackupPath = Paths.get(detectBackupUrl);
                // 获取去除文件名的目录路径
                Path detectLocalFileDir = detectLocalPath.getParent();
                Path detectBackupFileDir = detectBackupPath.getParent();
                // 检查目标目录是否存在，不存在则创建
                if (!Files.exists(detectLocalFileDir)) {
                    Files.createDirectories(detectLocalFileDir);
                }
                if (!Files.exists(detectBackupFileDir)) {
                    Files.createDirectories(detectBackupFileDir);
                }
                // 使用Files.copy方法复制文件，并指定REPLACE_EXISTING选项以替换目标位置已存在的文件
                Files.copy(detectBackupPath, detectLocalPath, StandardCopyOption.REPLACE_EXISTING);

            }

            log.info("文件已成功恢复");
            return true;
        } catch (IOException e) {
            log.error("恢复失败: " + e);
            return false;
        }
    }



    public boolean restoreAliyunImg(String originalLocalUrl, String originalBackupUrl,
                                   String detectLocalUrl, String detectBackupUrl) throws IOException {
        try {
            // 获取本地源路径 path值
            Path originalLocalPath = Paths.get(originalLocalUrl);
            // 获取去除文件名的目录路径
            Path originalLocalFileDir = originalLocalPath.getParent();
            // 检查目标目录是否存在，不存在则创建
            if (!Files.exists(originalLocalFileDir)) {
                Files.createDirectories(originalLocalFileDir);
            }
            aliOssTool.restoreImage(originalLocalUrl, originalBackupUrl);


            if(detectLocalUrl != null && !detectLocalUrl.isEmpty()){
                // 获取本地源路 path值
                Path detectLocalPath = Paths.get(detectLocalUrl);
                // 获取去除文件名的目录路径
                Path detectLocalFileDir = detectLocalPath.getParent();
                // 检查目标目录是否存在，不存在则创建
                if (!Files.exists(detectLocalFileDir)) {
                    Files.createDirectories(detectLocalFileDir);
                }
                aliOssTool.restoreImage(detectLocalUrl, detectBackupUrl);
            }
            log.info("文件已成功恢复");
            return true;
        } catch (IOException e) {
            log.error("恢复失败: " + e);
            return false;
        }
    }
}