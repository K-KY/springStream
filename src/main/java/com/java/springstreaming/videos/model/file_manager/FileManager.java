package com.java.springstreaming.videos.model.file_manager;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public List<Path> readFiles(String path) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Path.of(path), Files::isRegularFile)) {
            return getList(ds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Path> readDirs(String path) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Path.of(path), Files::isDirectory)) {
            return getList(ds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Path> getList(DirectoryStream<Path> ds) {
        List<Path> paths = new ArrayList<>();
        for (Path p : ds) {
            paths.add(p.getFileName());
        }
        return paths;
    }
}
