package com.rainbowletter.server.image.application.domain.model;

public record ImageUploadEvent(
    byte[] fileData, String filePath, String category
) {
}