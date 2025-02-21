package com.lzk.controller;


import com.lzk.entity.Result;
import com.lzk.entity.dto.GetGasDataDTO;
import com.lzk.entity.vo.ReportsTotalVo;
import com.lzk.service.DataService;
import com.lzk.service.ImgService;
import com.lzk.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class data {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.lzk.controller.data");

    @Value("${gas.idsTxt}")
    private String idsTxt;

    @Value("${gas.idsRestTxt}")
    private String idsRestTxt;

    @Autowired
    private ImgService imgService;
    @Autowired
    private FileTool fileTool;


    @Autowired
    private DataService dataService;

    /**
     * 从公司接口中获取所有的安检报告ID保存到txt文件中
     * @return null
     */
    @PostMapping("/getGasReportIdToFile")
    public Result getGasReportIdToFile(){
        logger.info("获取安检报告ID并保存到txt文件中");
        return dataService.getGasReportIdToFile();
    }

    /**
     * 根据TXT文件里面的reportID获取安检报告并下载到本地
     * @return null
     */
    @PostMapping("/getGasData")
    public Result getGasData(@RequestBody  GetGasDataDTO rqt){
        logger.info("采集安检报告到本地"+rqt);
        return dataService.getGasData(rqt);
    }

    /**
     * 用于查询TXT文件中公司报告总数
     * 以及本地报告总数
     * @return 总数
     */
    @GetMapping("/findTotal")
    public Result findTotal(){
        List<String> reports = fileTool.readLines(idsTxt);
        List<String> localReports = imgService.findReport(null);
        // 因为在数据库里面的报告不一定在远程仓库里面
        Set<String> reportsSet = new HashSet<>(reports);
        Set<String> localReportsSet = new HashSet<>(localReports);
        // 两者的交集
        reportsSet.retainAll(localReportsSet);
        return Result.success(new ReportsTotalVo(reportsSet.size(), reports.size()));
    }
}
