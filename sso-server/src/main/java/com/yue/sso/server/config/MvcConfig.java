package com.yue.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by 千里明月 on 2018/11/19.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private HandleInterceptor handleInterceptor;
///js/**,/images/**,/plugin/**,/script/**,/css/**,/partials/**,/sass/**,/script/**,/login.html
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handleInterceptor).addPathPatterns("/**").excludePathPatterns("/login.html").
                excludePathPatterns("/sso/server/login").
                excludePathPatterns("/sso/server/verifyToken").
//                excludePathPatterns("/sso/server/logout").
                excludePathPatterns("/css/**").
                excludePathPatterns("/js/**").
                excludePathPatterns("/error");

    }
}
