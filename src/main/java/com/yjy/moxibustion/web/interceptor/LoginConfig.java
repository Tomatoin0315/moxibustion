package com.yjy.moxibustion.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 杨景元
 * @date 2021/1/28 11:25
 */
@Component
public class LoginConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * app端的请求不能被放行
     * 所以取消了拦截器
     * 但是在web端完美运行
     */
    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/*")
                .excludePathPatterns("/login","/logout","/","/static/**","/api/**","/api/search");
    }*/
}
