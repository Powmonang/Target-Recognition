package com.defectscan.controller.user;

import com.defectscan.annotation.Log;
import com.defectscan.entity.User;
import com.defectscan.result.Result;
import com.defectscan.service.UserService;
import com.defectscan.utils.JwtUtils;
import com.defectscan.utils.SafeUtil;
import com.defectscan.vo.UserVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SafeUtil safeUtil;


    @PostMapping("/login")
    public Result login(@RequestBody UserVo request){
        log.info("用户登陆：{}",request.toString());
        //查询是否存在该账户(用户名）
        User findResult = userService.getByUsername(request.getUsername());
        if(findResult != null) {
            // 验证加密密码
            if(SafeUtil.checkPassword(request.getPassword(), findResult.getPassword())){
                //findResult.setUpdateTime(LocalDateTime.now());
                userService.login(findResult);
                // 创建用户信息映射MAP 用于创建Token
                Map<String, Object>userInfo = new HashMap<>();
                userInfo.put("id", findResult.getId());
                userInfo.put("username", findResult.getUsername());
                userInfo.put("password", findResult.getPassword());
                userInfo.put("type", findResult.getType()); //用户权限
                // 发放Token于前端
                String jwt = JwtUtils.generateJwt(userInfo);
                log.info("用户:{}登录成功!",request.getUsername());
                String msg = "用户" + request.getUsername() + "登录成功!";
                return Result.success(msg, jwt);
            }
            else{
                log.info(request.getUsername()+"登录失败：密码错误");
                return Result.error("密码错误");
            }
        }
        else {
            log.info(request.getUsername()+"登录失败：用户不存在");
            return Result.error("用户不存在");
        }
    }

    //@Log
    @PostMapping("/register")
    public Result registerUser(@RequestBody User request)
    {
        log.info("用户：{}注册",request.getUsername());
        //检查所注册用户名是否注册成功
        if(userService.registerUser(request)) {
            log.info(request.getUsername() + ":注册成功");
            return Result.success("注册成功", null);
        }else{
            log.info("注册失败，该用户名已存在");
            return Result.error("注册失败，该用户名已存在");
        }
    }

    //解析Jwt
    @PostMapping("/parseJwt")
    public Result parseJwt(@RequestBody User request)
    {
        log.info("请求解析Jwt:" + request.toString());
        String Jwt = request.getUsername();
        try {
            Claims map = JwtUtils.parseJWT(Jwt);
            User result = new User();
            result.setId((Integer) map.get("id"));
            result.setUsername(map.get("username").toString());
            result.setPassword(map.get("password").toString());
            result.setType(map.get("type").toString());
            log.info("Jwt解析成功:{}",map.toString());
            return Result.success(result);
        }catch (Exception e){
            log.info("Jwt解析错误");
            return Result.error("Jwt解析错误");
        }
    }

}
