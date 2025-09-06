package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus;

import java.time.LocalDateTime;

public record PetInitiatedLetterDetailResponse(
    Long id,
    PetInitiatedLetterStatus petInitiatedLetterStatus,
    LocalDateTime submitTime,
    LocalDateTime createdAt,
    String promptA,
    String promptB
) {
}
