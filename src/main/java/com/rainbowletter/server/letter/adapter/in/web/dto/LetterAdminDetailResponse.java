package com.rainbowletter.server.letter.adapter.in.web.dto;

import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.application.port.in.dto.LetterResponse;
import com.rainbowletter.server.pet.application.port.in.dto.PetDetailResponse;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyAdminResponse;
import com.rainbowletter.server.user.application.port.in.dto.UserInformationResponse;
import java.util.List;

public record LetterAdminDetailResponse(
    LetterAdminDetailUserInformationResponse user,
    PetDetailResponse pet,
    LetterResponse letter,
    ReplyAdminResponse reply,
    List<LetterAdminRecentResponse> recent
) {

    public static LetterAdminDetailResponse of(
        final UserInformationResponse userInformation,
        final Long letterCount,
        final PetDetailResponse petResponse,
        final LetterResponse letterResponse,
        final ReplyAdminResponse replyResponse,
        final List<LetterAdminRecentResponse> recentResponses
    ) {
        final var userResponse = LetterAdminDetailUserInformationResponse
            .from(userInformation, letterCount);
        return new LetterAdminDetailResponse(
            userResponse,
            petResponse,
            letterResponse,
            replyResponse,
            recentResponses
        );
    }

}
