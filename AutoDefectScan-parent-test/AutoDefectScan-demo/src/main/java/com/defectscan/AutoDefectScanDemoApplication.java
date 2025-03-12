package com.defectscan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
@EnableCaching//开发缓存注解功能
@EnableScheduling //开启任务调度
public class AutoDefectScanDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoDefectScanDemoApplication.class, args);
    }

}
