package com.lzk.mapper;

import com.lzk.entity.Model;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ModelMapper {
    List<Model> findModels(Model a);
    void addModel(Model a);
}
