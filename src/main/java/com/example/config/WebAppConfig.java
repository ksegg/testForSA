package com.example.config;

import com.example.handle.SessionInterceptorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebAppConfig implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptorHandler()).addPathPatterns("/*").excludePathPatterns("/login");
    }
}
