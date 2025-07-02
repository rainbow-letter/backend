package com.rainbowletter.server.user.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.user.application.port.in.validation.UserEmail;
import com.rainbowletter.server.user.application.port.in.validation.UserPassword;
import lombok.Value;

public interface ResetUserPasswordUseCase {

    void resetUserPassword(ResetUserPasswordCommand command);

    @Value
    @SuppressWarnings("ClassCanBeRecord")
    class ResetUserPasswordCommand {

        @UserEmail
        String email;

        @UserPassword
        String newPassword;

        public ResetUserPasswordCommand(final String email, final String newPassword) {
            this.email = email;
            this.newPassword = newPassword;
            validate(this);
        }

    }

}
