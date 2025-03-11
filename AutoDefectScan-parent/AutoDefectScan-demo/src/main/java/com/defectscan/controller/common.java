package com.defectscan.controller;

import com.defectscan.entity.Result;
import com.defectscan.entity.User;
import com.defectscan.service.UserService;
import com.defectscan.tools.JwtTool;
import com.defectscan.tools.SafeTool;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class common {
    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.defectscan.controller.common");

    @Autowired
    UserService userService;

    @Autowired
    JwtTool jwtTool;

    @Autowired
    SafeTool safeTool;

    @PostMapping("/login")
    public Result login(@RequestBody User request){
        logger.info(request.toString());
        User temp = new User();
        temp.setId(request.getId());
        //查询是否存在该账户
        List<User> findResult = userService.findUsers(temp);
        if(!findResult.isEmpty()) {
            // 获取查询到的用户
            User find = findResult.get(0);
            // 获取到加密密码
            String hashPs = find.getPs();
            // 验证加密密码
            if(safeTool.checkPassword(request.getPs(), hashPs)){
                // 修改最后登录时间
                User changeTemp = new User();
                changeTemp.setId(find.getId());
                changeTemp.setFinalLogin(LocalDateTime.now().toString());
                userService.changeUser(changeTemp);
                // 创建用户信息映射MAP 用于创建Token
                Map<String, Object>userInfo = new HashMap<>();
                userInfo.put("id", find.getId());
                userInfo.put("ps", find.getPs());
                userInfo.put("type", find.getType()); //用户权限
                // 发放Token于前端
                String jwt = jwtTool.makeJwt(userInfo);
                logger.info(request.getId()+"：登录成功");
                return Result.success("登录成功", jwt);
            }
            else{
                logger.info(request.getId()+"登录失败：密码错误");
                return Result.error("密码错误");
            }

        }
        else {
            logger.info(request.getId()+"登录失败：用户不存在");
            return Result.error("用户不存在");
        }
    }

    @PostMapping("/enroll")
    public Result enroll(@RequestBody User request)
    {
        //数据库主键重复性检查交予handle处理
        //获取原始密码
        String ps = request.getPs();
        String hashPs = safeTool.hashPassword(ps);
        //进行加密操作 再添加
        request.setPs(hashPs);
        userService.addUser(request);
        logger.info(request.getId()+":注册成功");
        return Result.success("注册成功", null);
    }


    //解析Jwt
    @PostMapping("/parseJwt")
    public Result parseJwt(@RequestBody User request)
    {
        logger.info("请求解析Jwt:" + request.toString());
        String Jwt = request.getId();
        try {
            Claims map = jwtTool.parseJwt(Jwt);
            User result = new User();
            result.setId(map.get("id").toString());
            result.setPs(map.get("ps").toString());
            result.setType(map.get("type").toString());
            return Result.success(result);
        }catch (Exception e){
            logger.info("Jwt解析错误");
            return Result.error("Jwt解析错误");
        }

    }
}
