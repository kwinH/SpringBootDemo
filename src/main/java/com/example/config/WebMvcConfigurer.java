package com.example.config;

import com.example.interceptor.OneInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new OneInterceptor()).addPathPatterns("/users1/**");
        super.addInterceptors(registry);
    }
}
