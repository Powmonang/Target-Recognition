package com.defectscan.schedule;

import com.defectscan.dto.GetGasDataDTO;
import com.defectscan.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class task {
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.task");

    @Value("${schedule.getGasData.limit}")
    private Integer limit;

    @Autowired
    private DataService dataService;

    @Scheduled(cron ="0 0 12 * * ?")
    public void getData() {
        GetGasDataDTO rqt = new GetGasDataDTO();
        rqt.setLimit(limit);
        logger.info("采集安检报告到本地"+rqt);
        dataService.getGasData(rqt);
    }

    // 每5天把远程仓库的所有报告id存到本地
    @Scheduled(cron = "0 0 0 1/5 * ?")
    public void getGasReportIdToFile() {
        dataService.getGasReportIdToFile();
    }

}
