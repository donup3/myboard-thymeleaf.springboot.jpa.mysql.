package com.dong.board.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("-----------------------------addInterceptor");
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/login");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
