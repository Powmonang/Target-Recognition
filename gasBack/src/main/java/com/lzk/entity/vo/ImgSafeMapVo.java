package com.lzk.entity.vo;

import com.lzk.entity.ImgType;
import com.lzk.entity.SafeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgSafeMapVo {
    private Map<String, List<SafeType>> imgSafeMap; //以图片内容ID为key
    private List<String> imgType; //图片类型内容
}
