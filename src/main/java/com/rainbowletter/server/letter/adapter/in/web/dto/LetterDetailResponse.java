package com.rainbowletter.server.letter.adapter.in.web.dto;

import com.rainbowletter.server.letter.application.port.in.dto.LetterResponse;
import com.rainbowletter.server.pet.application.port.in.dto.PetDetailResponse;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyResponse;
import jakarta.annotation.Nullable;

public record LetterDetailResponse(
    PetDetailResponse pet,
    LetterResponse letter,
    ReplyResponse reply
) {

    public static LetterDetailResponse of(
        final PetDetailResponse petResponse,
        final LetterResponse letterResponse,
        @Nullable final ReplyResponse replyResponse
    ) {
        return new LetterDetailResponse(petResponse, letterResponse, replyResponse);
    }

}
