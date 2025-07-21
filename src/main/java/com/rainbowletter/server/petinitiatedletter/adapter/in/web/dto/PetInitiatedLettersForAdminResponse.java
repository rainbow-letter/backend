package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus;

import java.time.LocalDateTime;

public record PetInitiatedLettersForAdminResponse(
    Long id,
    String petName,
    String summary,
    PetInitiatedLetterStatus petInitiatedLetterStatus,
    LocalDateTime createdAt
) {
}
