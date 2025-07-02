package com.rainbowletter.server.user.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import com.rainbowletter.server.user.application.domain.model.User.UserRole;
import com.rainbowletter.server.user.application.domain.model.User.UserStatus;
import com.rainbowletter.server.user.application.port.in.validation.UserEmail;
import com.rainbowletter.server.user.application.port.in.validation.UserPassword;
import java.time.LocalDateTime;
import lombok.Value;

public interface UserRegisterUseCase {

    Long registerUser(UserRegisterCommand command);

    @Value
    @SuppressWarnings("ClassCanBeRecord")
    class UserRegisterCommand {

        @UserEmail
        String email;

        @UserPassword
        String password;

        String phoneNumber;
        UserRole role;
        UserStatus status;
        OAuthProvider provider;
        String providerId;
        LocalDateTime lastLoggedIn;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;

        public UserRegisterCommand(final String email, final String password) {
            this.email = email;
            this.password = password;
            this.phoneNumber = null;
            this.role = UserRole.ROLE_USER;
            this.status = UserStatus.ACTIVE;
            this.provider = OAuthProvider.NONE;
            this.providerId = OAuthProvider.NONE.name();
            this.lastLoggedIn = null;
            this.createdAt = null;
            this.updatedAt = null;
            validate(this);
        }

    }

}
