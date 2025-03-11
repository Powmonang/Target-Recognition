package com.defectscan.controller;

import com.defectscan.entity.Img;
import com.defectscan.entity.Result;
import com.defectscan.service.ImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class upload {
    @Value("${upload.officeDir}")
    private String tempDir;

    @Autowired
    ImgService imgService;

    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.upload");

    // 上传图片存储到本地 未写入数据库
    @PostMapping("/uploadImg")
    public Result uploadImg(MultipartFile image){
        if(image!=null)
        {

            String originalFileName = image.getOriginalFilename();
            if(originalFileName==null) {
                logger.error("上传图像原始名称为空");
                return Result.error("上传图像原始名称为空");
            }
            int index = originalFileName.lastIndexOf(".");
            String extname = originalFileName.substring(index);
            String newFileName = UUID.randomUUID() +extname;
            // 使用Paths.get创建Path对象
            Path directory = Paths.get(tempDir);
            // 使用resolve方法拼接路径和文件名
            Path file = directory.resolve(newFileName);
            String FilePath =file.toString();
            logger.info("上传图像存储地址："+FilePath);
            try {
                image.transferTo(new File(FilePath));
            } catch (Exception e) {
                logger.error(e.toString());
                return Result.error();
            }
            return Result.success(newFileName, null);
//            return Result.success(FilePath, null);
        }
        else{
            logger.info("图像上传为空");
            return Result.error();
        }
    }

    //确定上传 如果用户没有点击上传表单的确定按钮 那么图片只会缓存在Temp文件夹
    //而不会存储在imgs文件夹 同时写入数据库中
    @PostMapping("/sureUpLoad")
    public Result sureUpLoad(@RequestBody Img request){
        request.setUploadTime(LocalDateTime.now().toString());
        logger.info(request.toString());
        String imgTempUrl = request.getUrl();
        // 将一个指向本地开发服务器（localhost:8080）上的上传路径的 URL 替换成一个新的路径或目录（tempDir）
        //TODO 上传的端口要注意
        imgTempUrl = imgTempUrl.replace("http://localhost:8080/uploadReturn/", tempDir);
        String destinationFile = imgTempUrl.replace("temp", "imgs"); // 目标文件绝对路径

        Path sourcePath = Paths.get(imgTempUrl);
        Path destinationPath = Paths.get(destinationFile);

        try {
            // 使用Files.copy方法复制文件，并指定REPLACE_EXISTING选项以替换目标位置已存在的文件
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // 删除源文件
            Files.delete(sourcePath);
            logger.info("文件已成功剪切到：" + destinationFile);

        } catch (IOException e) {
            logger.error(e.toString());
            return Result.error();
            // 在实际应用中，你可能需要更细致地处理异常，比如向用户报告错误
        }
        request.setUrl(request.getUrl().replace("uploadReturn", "upload"));
        imgService.addImg(request);
        return Result.success();
    }

    //删除未写入数据库的图片 即是图片缓存
//    @PostMapping("/delImgTemp")
//    public Result delImgTemp (String ImgPath){
//        logger.info("尝试删除："+ImgPath+"缓存");
//        File file = new File(ImgPath);
//        if (file.exists() && file.isFile()) {
//            boolean isDeleted = file.delete();
//            if (isDeleted) {
//                logger.info("删除完毕："+ImgPath+"缓存");
//            } else {
//                logger.info("删除失败："+ImgPath+"缓存");
//                return Result.error();
//            }
//        } else {
//            logger.info("删除失败："+ImgPath+"不是单一文件");
//            return Result.error();
//        }
//        return Result.success();
//    }
}