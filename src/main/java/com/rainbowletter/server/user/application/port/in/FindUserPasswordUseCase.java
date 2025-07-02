package com.rainbowletter.server.user.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.user.application.port.in.validation.UserEmail;
import lombok.Value;

public interface FindUserPasswordUseCase {

    void findUserPassword(FindUserPasswordQuery query);

    @Value
    @SuppressWarnings("ClassCanBeRecord")
    class FindUserPasswordQuery {

        @UserEmail
        String email;

        public FindUserPasswordQuery(final String email) {
            this.email = email;
            validate(this);
        }

    }

}
