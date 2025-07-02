package com.rainbowletter.server.image.application.domain.service;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.image.adapter.in.web.dto.ImageCreateResponse;
import com.rainbowletter.server.image.application.domain.model.ImageUploadEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ApplicationEventPublisher eventPublisher;

    public ImageCreateResponse uploadImage(MultipartFile file, String category) {
        String filePath = generateFilePath(category);
        try {
            byte[] fileData = file.getBytes();
            eventPublisher.publishEvent(new ImageUploadEvent(fileData, filePath, category));
        } catch (IOException e) {
            throw new RainbowLetterException("파일을 읽는 데 실패했습니다.", e);
        }
        return new ImageCreateResponse(filePath);
    }

    private String generateFilePath(String category) {
        String fileName = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return category + "/" + datePath + "/" + fileName + ".webp";
    }
}
