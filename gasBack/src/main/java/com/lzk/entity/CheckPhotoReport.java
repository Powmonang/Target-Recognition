package com.lzk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPhotoReport {
    private String oldTenantId; //原始租户ID
    private String checkReportId; //安检报告ID
    private String url;//图片地址
    private String photoTagId; //图片内容ID
    private String photoTag; //图片内容
}
