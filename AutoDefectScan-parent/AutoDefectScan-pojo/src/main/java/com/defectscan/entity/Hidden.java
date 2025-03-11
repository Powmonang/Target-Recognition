package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hidden {
    private String hiddenId; //隐患ID
    private String name; //隐患名称
    private String remark;//隐患描述
    private String hiddenLevelId;//隐患等级ID
    private String hiddenLevelName;//隐患等级名称

}
