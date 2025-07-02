package com.rainbowletter.server.user.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.user.application.port.in.validation.UserEmail;
import com.rainbowletter.server.user.application.port.in.validation.UserPassword;
import lombok.Value;

public interface ChangeUserPasswordUseCase {

    void changeUserPassword(ChangeUserPasswordCommand command);

    @Value
    @SuppressWarnings("ClassCanBeRecord")
    class ChangeUserPasswordCommand {

        @UserEmail
        String email;

        @UserPassword
        String password;

        @UserPassword
        String newPassword;

        public ChangeUserPasswordCommand(
            final String email,
            final String password,
            final String newPassword
        ) {
            this.email = email;
            this.password = password;
            this.newPassword = newPassword;
            validate(this);
        }

    }

}
