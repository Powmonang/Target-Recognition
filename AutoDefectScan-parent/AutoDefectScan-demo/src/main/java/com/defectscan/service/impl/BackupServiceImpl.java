package com.defectscan.service.impl;

import com.defectscan.entity.Img;
import com.defectscan.mapper.BackupMapper;
import com.defectscan.mapper.ImgMapper;
import com.defectscan.service.BackupService;
import com.defectscan.tools.ImgTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class BackupServiceImpl implements BackupService {

    @Autowired
    private BackupMapper backupMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private ImgTool imgTool;


    @Override
    public boolean localBackup(List<Img> imgs){
        log.info("开始备份照片至本地，数量：{}",imgs.size());
        // 备份方式
        String backupType = "1";
        // 遍历备份图片
        for (Img img : imgs) {
            try{
                // 如果该图片已经备份过，则将数据更新
                if(backupMapper.findByImgId(img.getId(), backupType) > 0)
                {
                    backupMapper.updateBackup(img, backupType);
                    continue;
                }
                // 将源数据库的isBackup字段设置为1,后续再更新数据库，以免出现过程异常但数据库已更改
                img.setIsBackup("1");
                // 获取备份实体（当写入数据库时，img的id即为backup_record表中的imgId
                Img backupImg = img;
                // 获取当前图片对应的本地源图片、检测图片路径
                String originalLocalUrl = img.getOriginalLocalUrl();
                String detectLocalUrl = img.getDetectLocalUrl();
                // 获取备份后的文件路径
                List<String> backupList = imgTool.backupImgToLocal(originalLocalUrl,detectLocalUrl);
                // 修改备份实体的源文件备份路径以及检测图片路径
                backupImg.setOriginalBackupUrl(backupList.get(0));
                if(backupList.size() > 1){
                    backupImg.setDetectBackupUrl(backupList.get(1));
                }

                // 添加备份数据到数据库
                backupMapper.addBackup(backupImg, backupType);
                // 将源数据库的isBackup字段设置为1
                backupMapper.setIsBackup(img);
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean aliyunBackup(List<Img> imgs) {
        log.info("开始备份照片至云端，数量：{}",imgs.size());
        // 备份方式
        String backupType = "2";
        // 遍历上传每个图片
        for (Img img : imgs) {
            try{
                // 如果该图片已经备份过，则将数据更新
                if(backupMapper.findByImgId(img.getId(), backupType) > 0)
                {
                    backupMapper.updateBackup(img, backupType);
                    continue;
                }
                // 将源数据库的isBackup字段设置为1,后续再更新数据库，以免出现过程异常但数据库已更改
                img.setIsBackup("1");
                // 获取备份实体（当写入数据库时，img的id即为backup_record表中的imgId
                Img backupImg = img;
                // 获取当前图片对应的本地源图片、检测图片路径
                String originalLocalUrl = img.getOriginalLocalUrl();
                String detectLocalUrl = img.getDetectLocalUrl();
                // 获取备份后的文件路径
                List<String> backupList = imgTool.backupImgToAliyun(originalLocalUrl,detectLocalUrl);
                // 修改备份实体的源文件路径以及检测图片路径
                backupImg.setOriginalBackupUrl(backupList.get(0));
                if(backupList.size() > 1){
                    backupImg.setDetectBackupUrl(backupList.get(1));
                }
                // 添加备份数据到数据库
                backupMapper.addBackup(backupImg, backupType);
                // 将源数据库的isBackup字段设置为1
                backupMapper.setIsBackup(img);
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean restoreLocalBackup(){
        log.info("开始从本地备份恢复照片至本地");
        // 从备份表中获取所有本地备份的数据
        List<Img> imgs = backupMapper.findLocalBackup("1");
        for (Img img : imgs) {
            try {
                // 如果该备份照片已经被删除
                if(imgMapper.findImgById(Long.valueOf(img.getId())) == null){
                    Img restoreImg = img;
                    // 获取图片的源本地路径、备份路径
                    String originalLocalUrl = img.getOriginalLocalUrl();
                    String detectLocalUrl = img.getDetectLocalUrl();
                    String originalBackupUrl = img.getOriginalBackupUrl();
                    String detectBackupUrl = img.getDetectBackupUrl();
                    // 获取恢复后的文件路径
                    imgTool.restoreLocalImg(originalLocalUrl,originalBackupUrl, detectLocalUrl,detectBackupUrl);
                    // 将源图片备份路径设为空
                    restoreImg.setOriginalBackupUrl(null);
                    restoreImg.setDetectBackupUrl(null);
                    //恢复图片信息，包括id值的恢复
                    backupMapper.restoreImg(restoreImg);
                    restoreImg.setIsBackup("1");
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean restoreAliyunBackup(){
        log.info("开始从云端备份恢复照片至本地");
        // 从备份表中获取所有阿里云备份的数据
        List<Img> imgs = backupMapper.findLocalBackup("2");
        for (Img img : imgs) {
            try {
                // 如果该备份照片已经被删除
                if(imgMapper.findImgById(Long.valueOf(img.getId())) == null){
                    Img restoreImg = img;
                    // 获取图片的源本地路径、备份路径
                    String originalLocalUrl = img.getOriginalLocalUrl();
                    String detectLocalUrl = img.getDetectLocalUrl();
                    String originalBackupUrl = img.getOriginalBackupUrl();
                    String detectBackupUrl = img.getDetectBackupUrl();
                    // 获取恢复后的文件路径
                    imgTool.restoreAliyunImg(originalLocalUrl,originalBackupUrl, detectLocalUrl,detectBackupUrl);
                    // 将源图片备份路径设为空
                    restoreImg.setOriginalBackupUrl(null);
                    restoreImg.setDetectBackupUrl(null);
                    //恢复图片信息，包括id值的恢复
                    backupMapper.restoreImg(restoreImg);
                    restoreImg.setIsBackup("1");
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return false;
            }
        }

        return true;
    }



}