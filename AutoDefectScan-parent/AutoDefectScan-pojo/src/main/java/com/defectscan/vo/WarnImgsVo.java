package com.defectscan.vo;

import com.defectscan.entity.WarnImgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarnImgsVo {
    private String reportId; //安检报告ID
    private List<List<WarnImgs>> rows; // 安检报告告警项
}
