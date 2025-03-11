package com.defectscan.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportsTotalVo {
    private Integer localTotal; //本地报告总数
    private Integer txtTotal; //txt报告总数
}
