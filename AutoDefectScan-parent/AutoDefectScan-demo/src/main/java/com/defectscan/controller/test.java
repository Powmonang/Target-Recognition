package com.defectscan.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@RequestMapping("/api")
@CrossOrigin("*") // 允许跨域
@Api(tags = "测试连接接口接口")
@Slf4j
public class test {
    @GetMapping("/test1")
    @ApiOperation("Get测试")
    public String test() throws IOException {
        log.info("测试get请求");
        try {
            return "111111111111111111111";
        } catch (Exception e) {
            return "失败";
        }
    }
}
