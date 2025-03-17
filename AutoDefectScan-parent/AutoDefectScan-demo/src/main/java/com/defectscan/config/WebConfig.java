package com.defectscan.config;


import com.defectscan.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Value("${upload.officeDir}")
//    private String tempDir;

    @Autowired
    LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/api/user/login").excludePathPatterns("/api/user/register");
    }
    /*
     *addResourceHandler:访问映射路径
     *addResourceLocations:资源绝对路径
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/uploadReturn/**").addResourceLocations("file:"+tempDir);
//    }
}
