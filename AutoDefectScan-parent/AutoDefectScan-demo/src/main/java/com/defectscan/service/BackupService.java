package com.defectscan.service;

import com.defectscan.entity.Img;

import java.io.IOException;
import java.util.List;

public interface BackupService {
    /**
     * 本地备份
     * @return
     */
    boolean localBackup();

//    /**
//     * 阿里云云端备份
//     * @param imgs
//     * @return
//     */
//    boolean aliyunBackup(List<Img> imgs);

    /**
     * 恢复本地备份
     * @return
     */
    boolean restoreLocalBackup();

//    /**
//     * 恢复云端备份
//     * @return
//     */
//    boolean restoreAliyunBackup();
}
