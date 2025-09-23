package com.java.springstreaming.videos.controller;

import com.java.springstreaming.videos.model.service.DirectoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("videos")
public class VideoController {

    private final DirectoryService directoryService;

    @GetMapping("file")
    public ResponseEntity<List<String>> getFileNames(String path) {
        log.info("Request Reading files from {}", path);
        try {
            if (path != null && !path.isEmpty()) {
                List<String> list = directoryService.readFiles(path)
                        .stream().map(Path::toString).toList();
                return getBody(list);

            }
            List<String> list = directoryService.readFiles()
                    .stream().map(Path::toString).toList();
            return getBody(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(e.getMessage()));
        }
    }

    @GetMapping("dir")
    public ResponseEntity<List<String>> getDirNames(String path) {
        log.info("Request Reading dirs from {}", path);
        try {
            if (path != null && !path.isEmpty()) {
                List<String> list = directoryService.readDirs(path)
                        .stream().map(Path::toString).toList();
                return getBody(list);

            }
                List<String> list = directoryService.readDirs()
                        .stream().map(Path::toString).toList();
                return getBody(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(e.getMessage()));
        }
    }

    private static ResponseEntity<List<String>> getBody(List<String> list) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }
}
