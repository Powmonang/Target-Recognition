package com.lzk.entity.vo;

import com.lzk.entity.Img;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgVo {
    private Long total; // 报告数量
    private List<List<Img>> reports;
}
