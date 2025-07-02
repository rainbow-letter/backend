package com.rainbowletter.server.image.application.domain.service;

import com.rainbowletter.server.image.application.domain.model.ImageUploadEvent;
import com.rainbowletter.server.slack.application.domain.service.SlackErrorReportService;
import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageUploadEventHandler {

    private final StorageService storageService;
    private final SlackErrorReportService slackErrorReportService;

    @Async
    @EventListener
    public void handleImageUpload(ImageUploadEvent event) {
        try (InputStream inputStream = new ByteArrayInputStream(event.fileData())) {
            byte[] webpData = convertToWebpWithResize(inputStream);
            storageService.uploadFile(webpData, "image/webp", event.filePath());
        } catch (Exception e) {
            log.error("[이벤트 처리 중 이미지 업로드 실패] {}", event.filePath(), e);
            slackErrorReportService.sendErrorReportToSlack(event.filePath(), e);
        }
    }

    private byte[] convertToWebpWithResize(InputStream inputStream) throws IOException {
        return ImmutableImage.loader()
            .fromStream(inputStream)
            .max(1280, 1280)
            .bytes(WebpWriter.DEFAULT);
    }
}
