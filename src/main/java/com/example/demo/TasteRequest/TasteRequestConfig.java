package com.example.demo.TasteRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TasteRequestConfig implements WebMvcConfigurer {

//    @Value("${file-save-path}")
//    private String file_save_path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFile/**")
                .addResourceLocations("file:" + "/Users/gutianyang/大四/上课/Web/find-yummy-backend/src/main/resources/static/uploadFile/");
    }
}
