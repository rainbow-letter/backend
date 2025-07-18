package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import java.time.LocalDateTime;

public record PetInitiatedLetterCollectResponse(
    String userEmail,
    boolean isPetInitiatedLetterEnabled,
    PetInitiatedLetterResponse petInitiatedLetterResponse,
    LocalDateTime scheduledSendTime
) {
}
