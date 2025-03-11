
package com.defectscan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.defectscan.entity.Result;
import com.defectscan.tools.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    JwtTool jwtTool;
    // 相当于拦截请求 如果返回true就放行 反之不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的URL
        String url = request.getRequestURL().toString();
        System.out.println("请求的URL："+url);
        // 获取请求头中的JWT令牌 放在了token里面
        String jwt = request.getHeader("authorization");
        if(!StringUtils.hasLength(jwt))
        {
            System.out.println("请求头token为空，返回未登录信息");
            Result ERROR = Result.error("warn:not login");
            // 类对象相当于JSON 转换为String
            String notLogin = JSONObject.toJSONString(ERROR);
            response.getWriter().write(notLogin);
        }
        else {
            try {

                jwtTool.parseJwt(jwt);
                //验证令牌
            } catch (Exception e) {
                System.out.println("解析令牌失败，返回未登录错误信息");
                Result ERROR = Result.error("warn: not login");
                // 类对象相当于JSON 转换为String
                String notLogin = JSONObject.toJSONString(ERROR);
                response.getWriter().write(notLogin);
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

//        System.out.println(jwt);
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            System.out.println("请求头名称：" + headerName + "，值：" + headerValue);
//        }