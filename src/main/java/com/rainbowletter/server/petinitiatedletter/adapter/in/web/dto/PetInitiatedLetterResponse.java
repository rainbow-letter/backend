package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus;

import java.time.LocalDateTime;

public record PetInitiatedLetterResponse(
    Long id,
    LocalDateTime createdAt,
    String summary,
    String content,
    PetInitiatedLetterStatus status,
    String userEmail,
    Long userId,
    boolean isPetInitiatedLetterEnabled,
    LocalDateTime submitTime
) {
}