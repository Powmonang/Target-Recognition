package com.lzk.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//图像类别表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgType {
    private String id;//类别ID
    private String type;//图像类别
    private String content;//内容
    private String mark;//备注
    public static ImgType createById(String id){
        ImgType result = new ImgType();
        result.setId(id);
        return result;
    }
}
