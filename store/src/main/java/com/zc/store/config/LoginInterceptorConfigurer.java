package com.zc.store.config;

import com.zc.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/web/register.html", "/web/login.html",
                        "/bootstrap3/**", "/css/**", "/images/**", "/js/**",
                        "/index.html", "/users/login", "/users/insertuser", "/users/update",
                        "/district/**", "/products/**");
    }
}
