package com.defectscan.service.impl;

import com.defectscan.constant.BaseContext;
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
    public boolean localBackup(){
        String username = BaseContext.getCurrentUsername();
        List<Img> imgs = imgMapper.findImgByUsername(username);
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
                // 获取当前图片路径
                String localUrl = img.getLocalDir();
                // 获取备份后的文件路径
                String backupDir = imgTool.backupImgToLocal(localUrl);
                // 修改备份实体的源文件备份路径以及检测图片路径
                backupImg.setBackupUrl(backupDir);
                // 添加备份数据到数据库
                backupMapper.addBackup(backupImg, backupType);
                // 更新源数据库中数据备份地址以及isBackup
                imgMapper.updateImg(backupImg);
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return false;
            }
        }
        return true;
    }

//    @Override
//    public boolean aliyunBackup(List<Img> imgs) {
//        log.info("开始备份照片至云端，数量：{}",imgs.size());
//        // 备份方式
//        String backupType = "2";
//        // 遍历上传每个图片
//        for (Img img : imgs) {
//            try{
//                // 如果该图片已经备份过，则将数据更新
//                if(backupMapper.findByImgId(img.getId(), backupType) > 0)
//                {
//                    backupMapper.updateBackup(img, backupType);
//                    continue;
//                }
//                // 将源数据库的isBackup字段设置为1,后续再更新数据库，以免出现过程异常但数据库已更改
//                img.setIsBackup("1");
//                // 获取备份实体（当写入数据库时，img的id即为backup_record表中的imgId
//                Img backupImg = img;
//                // 获取当前图片对应的本地源图片、检测图片路径
//                String localUrl = img.getLocalDir();
//                // 获取备份后的文件路径
//                String backupDir = imgTool.backupImgToAliyun(localUrl);
//                // 修改备份实体的源文件路径以及检测图片路径
//                backupImg.setBackupUrl(backupDir);
//                // 添加备份数据到数据库
//                backupMapper.addBackup(backupImg, backupType);
//                // 更新源数据库中数据备份地址以及isBackup
//                imgMapper.updateImg(backupImg);
//            }catch (Exception e){
//                log.error(e.getMessage(),e);
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public boolean restoreLocalBackup(){
        log.info("开始从本地备份恢复照片至本地");
        // 从备份表中获取所有该用户备份的数据
        String username = BaseContext.getCurrentUsername();
        List<Img> imgs = backupMapper.findLocalBackup("1");
        for (Img img : imgs) {
            try {
                if(img.getCreateUser().equals(username))
                {
                    // 如果该备份照片已经被删除
                    if(imgMapper.findImgById(Long.valueOf(img.getId())) == null){
                        Img restoreImg = img;
                        // 获取图片的源本地路径、备份路径
                        String localUrl = img.getLocalDir();
                        String backupUrl = img.getBackupUrl();
                        // 获取恢复后的文件路径
                        String aliyunUrl = imgTool.restoreLocalImg(localUrl,backupUrl);
                        //恢复图片信息，包括id值的恢复
                        img.setAliyunUrl(aliyunUrl);
                        backupMapper.restoreImg(restoreImg);
                    }
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
                return false;
            }
        }

        return true;
    }

//    @Override
//    public boolean restoreAliyunBackup(){
//        log.info("开始从云端备份恢复照片至本地");
//        // 从备份表中获取所有阿里云备份的数据
//        List<Img> imgs = backupMapper.findLocalBackup("2");
//        for (Img img : imgs) {
//            try {
//                // 如果该备份照片已经被删除
//                if(imgMapper.findImgById(Long.valueOf(img.getId())) == null){
//                    Img restoreImg = img;
//                    // 获取图片的源本地路径、备份路径
//                    String localUrl  = img.getLocalDir();
//                    String backupUrl = img.getBackupUrl();
//                    // 获取恢复后的文件路径
//                    imgTool.restoreAliyunImg(localUrl,backupUrl);
//                    //恢复图片信息，包括id值的恢复
//                    backupMapper.restoreImg(restoreImg);
//                }
//            }catch (Exception e){
//                log.error(e.getMessage(),e);
//                return false;
//            }
//        }
//
//        return true;
//    }



}