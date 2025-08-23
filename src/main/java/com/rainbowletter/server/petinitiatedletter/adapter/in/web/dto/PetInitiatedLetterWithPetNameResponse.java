package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import java.time.LocalDateTime;

public record PetInitiatedLetterWithPetNameResponse(
    Long letterId,
    LocalDateTime createdAt,
    LocalDateTime submitTime,
    String summary,
    String content,
    Long petId,
    String petName,
    String petImage
) {
}
