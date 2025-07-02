package com.rainbowletter.server.user.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.user.application.port.in.dto.TokenResponse;
import com.rainbowletter.server.user.application.port.in.validation.UserEmail;
import com.rainbowletter.server.user.application.port.in.validation.UserPassword;
import lombok.Value;

public interface UserLoginUseCase {

    TokenResponse login(UserLoginCommand command);

    @Value
    @SuppressWarnings("ClassCanBeRecord")
    class UserLoginCommand {

        private static final String LOGIN_MESSAGE = "이메일 및 비밀번호를 확인 해주세요.";

        @UserEmail(message = LOGIN_MESSAGE)
        String email;

        @UserPassword(message = LOGIN_MESSAGE)
        String password;

        public UserLoginCommand(final String email, final String password) {
            this.email = email;
            this.password = password;
            validate(this);
        }

    }

}
