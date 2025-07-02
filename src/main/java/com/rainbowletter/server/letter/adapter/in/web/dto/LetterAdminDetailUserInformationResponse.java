package com.rainbowletter.server.letter.adapter.in.web.dto;

import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import com.rainbowletter.server.user.application.domain.model.User.UserRole;
import com.rainbowletter.server.user.application.port.in.dto.UserInformationResponse;
import java.time.LocalDateTime;

public record LetterAdminDetailUserInformationResponse(
    Long id,
    String email,
    String phoneNumber,
    UserRole role,
    OAuthProvider provider,
    Long count,
    LocalDateTime lastLoggedIn,
    LocalDateTime lastChangedPassword,
    LocalDateTime createdAt
) {

    public static LetterAdminDetailUserInformationResponse from(
        final UserInformationResponse userInformation,
        final Long letterCount
    ) {
        return new LetterAdminDetailUserInformationResponse(
            userInformation.id(),
            userInformation.email(),
            userInformation.phoneNumber(),
            userInformation.role(),
            userInformation.provider(),
            letterCount,
            userInformation.lastLoggedIn(),
            userInformation.lastChangedPassword(),
            userInformation.createdAt()
        );
    }

}
