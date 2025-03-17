package com.defectscan.service;

import com.defectscan.dto.ImgPageQueryDTO;
import com.defectscan.dto.UrlRequestDTO;
import com.defectscan.entity.Img;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.result.PageResult;


public interface ImgService {
    /**
     * 添加图片信息
     * @param a
     */
    void addImg(Img a);

    /**
     * 调用ai进行图像检测
     * @param request
     * @return
     */
    public ReturnDetectData detectImages(UrlRequestDTO request);

    /**
     * 进行分页查询
     * @param imgPageQueryDTO
     * @return
     */
    PageResult pageQuery(int page, int pageSize, ImgPageQueryDTO imgPageQueryDTO);

    /**
     * 编辑图片
     * @param img
     * @return
     */
    boolean updateImg(Img img);

    /**
     * 删除图片
     * @param id
     * @return
     */
    boolean deleteImg(Long id);

}
