package com.lzk.mapper;

import com.lzk.entity.InferImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InferImgMapper {
    void addInfer(InferImg a);

    List<InferImg> findInfers(InferImg a);
    List<InferImg> findReportIdDistinctPage(@Param("a") InferImg a, Integer page, Integer PageSize);

    void changeInfer(InferImg a);
}
