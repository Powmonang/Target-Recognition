package com.defectscan.service;

import com.defectscan.entity.Result;
import com.defectscan.dto.GetGasDataDTO;

public interface DataService {
    String getToken();
    Result getGasReportIdToFile();
    Result getGasData(GetGasDataDTO rqt);
}
