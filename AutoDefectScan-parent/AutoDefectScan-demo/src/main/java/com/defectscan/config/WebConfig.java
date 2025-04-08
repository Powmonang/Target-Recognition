package com.defectscan.config;


import com.defectscan.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    LoginCheckInterceptor loginCheckInterceptor;

    public WebConfig() {
        System.out.println("WebConfig initialized");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/api/user/login","/api/user/register");
    }
}
