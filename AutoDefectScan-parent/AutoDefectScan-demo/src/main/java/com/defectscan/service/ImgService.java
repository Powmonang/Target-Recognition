package com.defectscan.service;

import com.defectscan.vo.ImgPageQueryVO;
import com.defectscan.entity.Img;
import com.defectscan.entity.ReturnDetectData;
import com.defectscan.result.PageResult;

import java.util.List;


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
    public ReturnDetectData detectImages(List<String> request);

    /**
     * 进行分页查询
     * @param imgPageQueryDTO
     * @return
     */
    PageResult pageQuery(int page, int pageSize, ImgPageQueryVO imgPageQueryDTO);

    /**
     * 编辑图片
     * @param id
     * @param mark
     * @return
     */
    boolean updateImg(int id, String mark);

    /**
     * 删除图片
     * @param id
     * @return
     */
    boolean deleteImg(Long id);

}
