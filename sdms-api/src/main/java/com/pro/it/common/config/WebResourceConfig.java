package com.pro.it.common.config;

import com.pro.it.sdms.enums.ObjectTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebResourceConfig implements WebMvcConfigurer {

    @Value("${file.upload.filePath}")
    private String FILE_REPO;

    @Value("${file.download.divider}")
    private String FILE_DOWNLOAD;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FILE_DOWNLOAD+ ObjectTypeEnum.CLUB.toString() +"/**")
                .addResourceLocations("file:" + FILE_REPO + File.separator + ObjectTypeEnum.CLUB.toString() + File.separator);
        registry.addResourceHandler(FILE_DOWNLOAD+ ObjectTypeEnum.AVATAR.toString() +"/**")
                .addResourceLocations("file:" + FILE_REPO + File.separator + ObjectTypeEnum.AVATAR.toString() + File.separator);
        registry.addResourceHandler(FILE_DOWNLOAD+ ObjectTypeEnum.ORGANIZATION.toString() +"/**")
                .addResourceLocations("file:" + FILE_REPO + File.separator + ObjectTypeEnum.ORGANIZATION.toString() + File.separator);
    }
}
