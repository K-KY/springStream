package com.java.springstreaming.videos.model.file_manager;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static FileManager fileManager;
    private FileManager() {}
    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

    public List<Path> readFiles(String path) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(validatePath(path), Files::isRegularFile)) {
            return getList(ds);
        } catch (IOException e) {
            throw new IllegalStateException("유효하지 않은 경로입니다.");
        }
    }

    public List<Path> readDirs(String path) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(validatePath(path), Files::isRegularFile)) {
            return getList(ds);
        } catch (IOException e) {
            throw new IllegalStateException("유효하지 않은 경로입니다.");
        }
    }

    //
    private static List<Path> getList(DirectoryStream<Path> ds) {
        List<Path> paths = new ArrayList<>();
        for (Path p : ds) {
            paths.add(p.getFileName());
        }
        return paths;
    }

    //검증
    private static Path validatePath(String path) {
        String in = (path == null) ? "" : path;
        Path raw = Path.of(in);
        if (raw.isAbsolute() || in.contains("..")) {
            throw new IllegalArgumentException("유효하지 않은 경로입니다.");
        }
        return raw;
    }
}
