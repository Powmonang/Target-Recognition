package com.defectscan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.defectscan.constant.BaseContext;
import com.defectscan.result.Result;
import com.defectscan.tools.JwtTool;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//
@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
    JwtTool jwtTool;
    // 相当于拦截请求 如果返回true就放行 反之不放行

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的URL
        String url = request.getRequestURL().toString();
        log.info("请求的url:{}", url);
        // 获取请求头中的JWT令牌 放在了token里面
        String jwt = request.getHeader("Authorization");
        if(!StringUtils.hasLength(jwt))
        {
            log.info("请求头token为空，返回未登录信息");
            Result error = Result.error("warn:not login");
            // 类对象相当于JSON 转换为String
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        else {
            try {
                Claims claims = jwtTool.parseJwt(jwt);

                String username = claims.get("username", String.class);
                String password = claims.get("password", String.class);
                String type = claims.get("type", String.class);

                BaseContext.setCurrentUsername(username);
                BaseContext.setCurrentPassword(password);
                BaseContext.setCurrentType(type);
                //验证令牌
            } catch (Exception e) {
                log.info("解析令牌失败，返回未登录错误信息");
                Result error = Result.error("warn: not login");
                // 类对象相当于JSON 转换为String
                String notLogin = JSONObject.toJSONString(error);
                response.getWriter().write(notLogin);
                return false;
            }
        }
        return true;
    }

    // 相当于拦截请求 如果返回true就放行 反之不放行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    //页面渲染完毕后 再运行这个方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
