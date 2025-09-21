package com.java.springstreaming.videos.controller;

import com.java.springstreaming.videos.model.file_manager.FileManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("videos")
public class VideoController {

    private static final FileManager fileManager = new FileManager();

    @GetMapping
    public List<String> getVideos() {
        return fileManager.readFiles("src/main/java/com/java/springstreaming/videos/model/file_manager")
                .stream().map(s -> s.toString()).collect(Collectors.toList());
    }
}
