package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import java.time.LocalDateTime;

public record PetInitiatedLetterSimpleResponse(
    Long id,
    LocalDateTime createdAt,
    String summary,
    String content,
    boolean readStatus
) {
}
