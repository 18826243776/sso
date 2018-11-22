package com.yue.sso.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by 千里明月 on 2018/11/19.
 */
@Configuration
public class MvnConfig implements WebMvcConfigurer {

    @Autowired
    private HandleInter handleInter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handleInter).addPathPatterns("/**").excludePathPatterns("classpath:/static/**")
        .excludePathPatterns("/error");
    }
}
