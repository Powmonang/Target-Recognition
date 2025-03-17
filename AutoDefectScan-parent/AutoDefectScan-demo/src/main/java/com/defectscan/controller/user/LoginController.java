package com.defectscan.controller.user;

import com.defectscan.entity.User;
import com.defectscan.result.Result;
import com.defectscan.service.UserService;
import com.defectscan.tools.JwtTool;
import com.defectscan.tools.SafeTool;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
@RequestMapping("/api/user")
public class LoginController {
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
        temp.setUsername(request.getUsername());
        //查询是否存在该账户
        User find = userService.findUsers(temp);
        if(find != null) {
            // 获取到加密密码
            String hashPs = find.getPassword();
            logger.info("加密密码：{}", hashPs);
            // 验证加密密码
            if(safeTool.checkPassword(request.getPassword(), hashPs)){
                // 修改最后登录时间
                User changeTemp = new User();
                changeTemp.setUsername(find.getUsername());
                changeTemp.setFinalLogin(LocalDateTime.now().toString());
                userService.changeUser(changeTemp);
                // 创建用户信息映射MAP 用于创建Token
                Map<String, Object>userInfo = new HashMap<>();
                userInfo.put("username", find.getUsername());
                userInfo.put("password", find.getPassword());
                userInfo.put("userType", find.getUserType()); //用户权限
                // 发放Token于前端
                String jwt = jwtTool.makeJwt(userInfo);
                logger.info(request.getUsername()+"：登录成功");
                return Result.success("登录成功", jwt);
            }
            else{
                logger.info(request.getUsername()+"登录失败：密码错误");
                return Result.error("密码错误");
            }

        }
        else {
            logger.info(request.getUsername()+"登录失败：用户不存在");
            return Result.error("用户不存在");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User request)
    {
        try{
            //获取原始密码
            String ps = request.getPassword();
            String hashPs = safeTool.hashPassword(ps);
            //进行加密操作 再添加
            request.setPassword(hashPs);
            userService.addUser(request);
            logger.info(request.getUsername()+":注册成功");
            return Result.success("注册成功", null);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.error("用户名已存在");
        }
    }


}
