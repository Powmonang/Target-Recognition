package com.defectscan.mapper;

import com.defectscan.entity.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BackupMapper {
    int findByImgId(int imgId, String backupType);

    void updateBackup(@Param("a") Img img, String backupType);

    void addBackup(@Param("a") Img img, String backupType);

    void setIsBackup(Img img);

    List<Img> findLocalBackup(String backupType);

    void restoreImg(Img img);
}
