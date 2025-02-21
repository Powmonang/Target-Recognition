package com.lzk.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.officeDir}")
    private String tempDir;

    @Autowired
    LoginInterceptor loginInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry)
//    {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/enroll").excludePathPatterns("/uploadReturn/**").excludePathPatterns("/upload/**");
//    }
    /*
     *addResourceHandler:访问映射路径
     *addResourceLocations:资源绝对路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadReturn/**").addResourceLocations("file:"+tempDir);
    }
}
