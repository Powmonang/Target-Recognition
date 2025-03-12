package com.defectscan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasReport {
    private String id; // 报告id
    private List<CheckPhotoReport> checkPhotoReports;
    private List<Hidden> hiddens;
}
