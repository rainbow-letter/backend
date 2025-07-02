package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.user.application.port.in.UserLoginUseCase;
import com.rainbowletter.server.user.application.port.in.dto.TokenResponse;
import com.rainbowletter.server.user.application.port.out.NativeUserLoginPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UserLoginService implements UserLoginUseCase {

    private final NativeUserLoginPort nativeUserLoginPort;

    @Override
    public TokenResponse login(final UserLoginCommand command) {
        final String accessToken = nativeUserLoginPort.login(
            command.getEmail(),
            command.getPassword()
        );
        return new TokenResponse(accessToken);
    }

}
