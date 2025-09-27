package com.java.springstreaming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 엔드포인트
                        .allowedOrigins("http://localhost:5173", "https://kky.tail0a6d17.ts.net")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 필요한 메서드
                        .allowedHeaders("*")
                        .allowCredentials(true); // 쿠키/인증정보 포함 허용 시
            }
        };
    }

}
