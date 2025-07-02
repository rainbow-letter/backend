package com.rainbowletter.server.user.application.port.in.dto;

import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.domain.model.User.UserRole;
import java.time.LocalDateTime;

public record UserInformationResponse(
    Long id,
    String email,
    String phoneNumber,
    UserRole role,
    OAuthProvider provider,
    LocalDateTime lastLoggedIn,
    LocalDateTime lastChangedPassword,
    LocalDateTime createdAt
) {

    public static UserInformationResponse from(final User user) {
        return new UserInformationResponse(
            user.getId().value(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getProvider(),
            user.getLastLoggedIn(),
            user.getLastChangedPassword(),
            user.getCreatedAt()
        );
    }

}
