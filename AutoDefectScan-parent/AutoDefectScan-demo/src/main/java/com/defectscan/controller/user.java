package com.defectscan.controller;

import com.defectscan.entity.Result;
import com.defectscan.entity.User;
import com.defectscan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class user {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.user");

    @Autowired
    UserService userService;

    @PostMapping("/findUsers")
    public Result findUsers(@RequestBody User request){
        logger.info("查询用户："+ request.toString());
        return Result.success(userService.findUsers(request));
    }

    @PostMapping("/changeUser")
    public Result changeUser(@RequestBody User request){
        logger.info("修改用户："+request.toString());
        userService.changeUser(request);
        return Result.success();
    }
}
