package com.lzk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeImg {
    private String id;//逻辑主键
    private String oper; //操作者（ID）
    private String imgId; // 图像ID
    private String safeType;//隐患ID
    private String keyTime; //时间戳
    private String indexUrl; // 坐标文件路径
    private String flag; //FLAG无实际意义
}
