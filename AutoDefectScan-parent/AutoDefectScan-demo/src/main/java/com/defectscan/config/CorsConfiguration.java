package com.defectscan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  注：该配置类可以不需要，因为再局域网进行前后端联调过程中需要配置某个来源进行访问，但由于找不到
 *  就只能允许所有来源进行访问，但是使用该配置还不行，只能再controller类中添加  ”@CrossOrigin("*") // 允许跨域“
 */


@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置全局 CORS 策略
        registry.addMapping("/**")  // 允许所有路径跨域
                .allowedOrigins("*")  // 允许所有来源进行访问
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 允许的请求方法
                .allowedHeaders("*")  // 允许所有请求头
                .allowCredentials(true)  // 是否允许携带凭证
                .maxAge(3600);  // 预检请求的缓存时间
    }
}