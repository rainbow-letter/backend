package com.rainbowletter.server.user.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.user.application.port.in.validation.UserEmail;
import com.rainbowletter.server.user.application.port.in.validation.UserPhoneNumber;
import lombok.Value;

public interface UpdateUserPhoneNumberUseCase {

    void updateUserPhoneNumber(UpdateUserPhoneNumberCommand command);

    @Value
    class UpdateUserPhoneNumberCommand {

        @UserEmail
        String email;

        @UserPhoneNumber
        String phoneNumber;

        public UpdateUserPhoneNumberCommand(final String email) {
            this.email = email;
            this.phoneNumber = null;
        }

        public UpdateUserPhoneNumberCommand(final String email, final String phoneNumber) {
            this.email = email;
            this.phoneNumber = phoneNumber;
            validate(this);
        }

    }

}
