package com.java.springstreaming.videos.model.service;

import com.java.springstreaming.videos.model.file_manager.FileManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class DirectoryService {

    private final FileManager fileManager;

    public List<Path> readFiles() {
        return fileManager.readFiles();
    }

    public List<Path> readFiles(String path) {
        return fileManager.readFiles(path);
    }

    public List<Path> readDirs() {
        return fileManager.readDirs();
    }

    public List<Path> readDirs(String path) {
        return fileManager.readDirs(path);
    }

    public List<Path> readHlsDirInPath(String path) {
        return fileManager.readHlsDirInPath(path);
    }
}
