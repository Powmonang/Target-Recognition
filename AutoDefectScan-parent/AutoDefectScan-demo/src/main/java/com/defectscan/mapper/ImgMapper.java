package com.defectscan.mapper;

import com.defectscan.dto.AiDetectDTO;
import com.defectscan.vo.ImgPageQueryVO;
import com.defectscan.entity.Img;
import com.defectscan.vo.ReturnPageImgVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImgMapper {
    void addImg(Img a);

    AiDetectDTO selectAiDetectDTOByUrl(String localDir);

    void updateImgDetect(Img img);

    long pageSum(ImgPageQueryVO imgPageQueryDTO);

    List<ReturnPageImgVO> pageQuery(int startIndex, int pageSize, @Param("a") ImgPageQueryVO imgPageQueryDTO);

    Img findImgById(Long id);

    List<Img> findImgByUsername(String username);

    int isExist(int id);

    // 编辑图片
    void updateImg(Img img);

    // 删除图片
    void deleteImg(@Param("a") Img img);
}
