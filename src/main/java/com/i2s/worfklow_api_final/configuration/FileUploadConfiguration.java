package com.i2s.worfklow_api_final.configuration;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("D:\\tomcatTempFiles");
        return factory.createMultipartConfig();
    }

}
