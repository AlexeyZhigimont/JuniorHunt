package com.juniorhunt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/profile/**").addResourceLocations("file://" + uploadPath + "/");
        //registry.addResourceHandler("/img/**").addResourceLocations("file://"+uploadPath + "/");
        //registry.addResourceHandler("/img/**").addResourceLocations("classpath:/profile/");
        //registry.addResourceHandler("/resources/profile/**").addResourceLocations("classpath:/profile/");
        registry.addResourceHandler("/profile/**").addResourceLocations("classpath:/profile/");

    }
}
