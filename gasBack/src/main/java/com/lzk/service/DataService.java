package com.lzk.service;

import com.lzk.entity.Result;
import com.lzk.entity.dto.GetGasDataDTO;

public interface DataService {
    String getToken();
    Result getGasReportIdToFile();
    Result getGasData(GetGasDataDTO rqt);
}
