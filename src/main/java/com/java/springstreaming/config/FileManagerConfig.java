package com.java.springstreaming.config;

import com.java.springstreaming.videos.model.file_manager.FileManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileManagerConfig {

    @Value("${base.store}")
    private String baseStore;

    @Bean
    public FileManager fileManager() {
        return new FileManager(baseStore);
    }
}
