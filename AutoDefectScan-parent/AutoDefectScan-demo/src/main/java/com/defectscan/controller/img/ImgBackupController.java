package com.defectscan.controller.img;


import com.defectscan.constant.MessageConstant;
import com.defectscan.entity.Img;
import com.defectscan.result.Result;
import com.defectscan.service.BackupService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/img/backup")
@CrossOrigin("*") // 允许跨域
@Api(tags = "图片备份相关接口")
@Slf4j
public class ImgBackupController {
    @Autowired
    private BackupService backupService;

    @PostMapping("/local")
    public Result localBackup(@RequestBody List<Img> imgs) {
        log.info("开始文件备份");
        if(backupService.localBackup(imgs)){
            return Result.success("图片备份成功");
        }
        log.info("图片备份失败");
        return Result.error("图片备份失败");
    }

    @PostMapping("/aliyun")
    public Result aliyunBackup(@RequestBody List<Img> imgs) {
        if(backupService.aliyunBackup(imgs)){
            return Result.success("图片备份成功");
        }
        log.info("图片备份失败");
        return Result.error("图片备份失败");
    }

    @PostMapping("/restoreLocal")
    public Result restoreLocalBackup() {
        if(backupService.restoreLocalBackup()){
            return Result.success("备份图片恢复成功");
        }
        log.info("备份图片恢复失败");
        return Result.error("备份图片恢复失败");
    }

    @PostMapping("/restoreAliyun")
    public Result restoreAliyunBackup() {
        if(backupService.restoreAliyunBackup()){
            return Result.success("备份图片恢复成功");
        }
        log.info("备份图片恢复失败");
        return Result.error("备份图片恢复失败");
    }




}
