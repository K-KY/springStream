package com.java.springstreaming.videos.model.file_manager;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class FileManager {

    private final Path BASE_PATH;

    public FileManager(String baseStore) {
        BASE_PATH = Path.of(baseStore);
    }

    public List<Path> readFiles() {
        log.info("Reading files from {}", "/");
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(BASE_PATH, Files::isRegularFile)) {
            return getList(ds);
        } catch (IOException e) {
            throw new IllegalStateException("유효하지 않은 경로입니다." + "/");
        }
    }

    public List<Path> readFiles(String path) {
        log.info("Reading files from {}", "/");
        Path p = validatePath(path);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(BASE_PATH.resolve(p), Files::isRegularFile)) {
            return getList(ds);
        } catch (IOException e) {
            throw new IllegalStateException("유효하지 않은 경로입니다." + "/");
        }
    }

    public List<Path> readDirs() {
        log.info("Reading dirs from {}", "/");
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(BASE_PATH, Files::isDirectory)) {
            return getList(ds);
        } catch (IOException e) {
            throw new IllegalStateException("유효하지 않은 경로입니다." + "/");
        }
    }

    public List<Path> readDirs(String path) {
        log.info("Reading dirs from {}", "/");
        Path p = validatePath(path);
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(BASE_PATH.resolve(p), Files::isDirectory)) {
            return getList(ds);
        } catch (IOException e) {
            throw new IllegalStateException("유효하지 않은 경로입니다." + "/");
        }
    }

    public List<Path> readHlsDirInPath(String path) {
        if (!isExist(path)) {
            throw new NoSuchElementException("해당하는 제목을 찾지 못했습니다.");
        }
        List<Path> paths = readDirs(path + "/" + "hls/");
        if (paths.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }
        return paths;
    }

    public boolean isExist(String path) {
        return readDirs().stream().map(Path::toString).anyMatch(s -> s.equals(path));
    }


    //
    private static List<Path> getList(DirectoryStream<Path> ds) {
        List<Path> paths = new ArrayList<>();
        for (Path p : ds) {
            if(isHidden(p)) {
                continue;
            }
            paths.add(p.getFileName());
        }
        return paths;
    }

    private static boolean isHidden(Path p) {
        try {
            Path name = p.getFileName();
            if (name != null && name.toString().startsWith(".")) {
                return true;
            }
            return java.nio.file.Files.isHidden(p);
        } catch (java.io.IOException e) {
            return true; // 상태 확인 실패 시 보이지 않게 처리
        }
    }


    //검증
    private Path validatePath(String path) {
        log.info("Validating path {}", path);

        String in = (path == null) ? "" : path.trim();
        if (in.equals("?") || in.isEmpty()) {
            return BASE_PATH;
        }

        if (in.indexOf('\0') >= 0) {
            throw new IllegalArgumentException("유효하지 않은 문자");
        }
        if (in.startsWith("file:") || in.startsWith("classpath:")) {
            throw new IllegalArgumentException("유효하지 않은 경로 : " + path);
        }

        Path rel = Path.of(in);
        if (rel.isAbsolute()) {
            throw new IllegalArgumentException("절대경로 금지");
        }

        Path candidate = BASE_PATH.resolve(rel).normalize();
        try {
            Path real = candidate.toRealPath(LinkOption.NOFOLLOW_LINKS); // 최종 물리 경로 확정
            if (!real.startsWith(BASE_PATH)) {
                throw new SecurityException("루트 밖 접근 차단");
            }
            return real;
        } catch (IOException e) {
            throw new IllegalStateException("경로가 존재하지 않음: " + in, e);
        }
    }

}
