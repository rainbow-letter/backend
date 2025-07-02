package com.rainbowletter.server.common.adapter.out.infrastructure;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class FileManager {

    public void saveFile(
        final Path filePath,
        final MultipartFile file,
        final String basePath,
        final String... dirName
    ) {
        try (final InputStream inputStream = file.getInputStream()) {
            createDir(basePath, dirName);
            Files.copy(inputStream, filePath);
        } catch (final IOException exception) {
            throw new RainbowLetterException("fail.save.file", exception.getMessage());
        }
    }

    public void createDir(final String basePath, final String... dirName) {
        final File baseDir = getAbsolutePath(basePath, dirName).toFile();
        if (!baseDir.exists() && baseDir.mkdirs()) {
            log.info("폴더를 생성하였습니다. [{}]", basePath + "/" + String.join("/", dirName));
        }
    }

    public Path getAbsolutePath(final String basePath, final String... paths) {
        return Paths.get(basePath, paths).toAbsolutePath();
    }

    public File[] searchFilesByFileName(final Path searchDirPath, final String fileName) {
        final File searchTargetDir = searchDirPath.toFile();
        return searchTargetDir.listFiles(file ->
            file.isFile() && file.getName().startsWith(fileName)
        );
    }

    public File getFirstFile(final File[] files) {
        if (Objects.isNull(files) || files.length == 0) {
            throw new RainbowLetterException("not.found.file");
        }
        return files[0];
    }

    public MediaType parseMediaType(final Path path) {
        try {
            return MediaType.parseMediaType(new Tika().detect(path));
        } catch (final IOException exception) {
            throw new RainbowLetterException("not.found.media-type");
        }
    }

    public void deleteFile(final Path filePath) {
        try {
            Files.delete(filePath);
        } catch (final IOException exception) {
            throw new RainbowLetterException("fail.delete.file", exception.getMessage());
        }
    }

}
