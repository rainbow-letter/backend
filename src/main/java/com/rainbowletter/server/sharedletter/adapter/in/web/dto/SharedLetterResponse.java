package com.rainbowletter.server.sharedletter.adapter.in.web.dto;

import com.rainbowletter.server.pet.application.port.in.dto.PetSimpleSummary;
import com.rainbowletter.server.sharedletter.application.domain.model.RecipientType;

public record SharedLetterResponse(
    Long id,
    String content,
    RecipientType recipientType,
    PetSimpleSummary pet
) {
}