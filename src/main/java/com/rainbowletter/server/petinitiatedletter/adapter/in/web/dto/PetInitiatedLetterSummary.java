package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import java.time.LocalDateTime;

public record PetInitiatedLetterSummary(
    Long letterId,
    LocalDateTime createdAt,
    String content,
    Long petId,
    String petName,
    String petImage
) {
}
