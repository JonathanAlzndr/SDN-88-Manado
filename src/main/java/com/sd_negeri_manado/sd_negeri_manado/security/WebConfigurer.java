package com.sd_negeri_manado.sd_negeri_manado.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Menentukan direktori untuk gambar yang di-upload
        Path uploadDir = Paths.get("public/images");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        // Menambahkan resource handler agar file di direktori tersebut dapat diakses
        registry.addResourceHandler("/public/images/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // Menambahkan handler untuk static resources di dalam folder /static (opsional)
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}


