package com.java.springstreaming.videos.model.FileManager;

import com.java.springstreaming.videos.model.file_manager.FileManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileManagerTest {
    private final FileManager fileManager
            = new FileManager("src/main/java/com/java/springstreaming/videos/model/file_manager");
    @Test
    @DisplayName("지정한 경로의 \"파일\"을 전부 읽는다.")
    public void fileReadTest() {
        //테스트의 성공을 보장해야 하므로 테스트가 실행 되려면 존재 해야하는 파일로 검증
        List<Path> read = fileManager
                .readFiles();
        assertThat(read.contains(Path.of("FileManager.java"))).isTrue();
    }

    @Test
    @DisplayName("지정한 경로의 \"디렉터리\"를 전부 읽는다.")
    public void fileReadTest2() {
        List<Path> read = fileManager
                .readDirs();
        assertThat(read.contains(Path.of("file_manager")));
    }

    @Test
    @DisplayName("유효하지 않은 경로 차단")
    public void fileReadTest3() {
        assertThatThrownBy(() ->fileManager.readDirs("../")).isInstanceOf(SecurityException.class);
    }
}
