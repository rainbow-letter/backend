package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.in.UserRegisterUseCase;
import com.rainbowletter.server.user.application.port.out.ExistsUserPort;
import com.rainbowletter.server.user.application.port.out.RegisterUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UserRegisterService implements UserRegisterUseCase {

    private final TimeHolder timeHolder;
    private final PasswordEncoder passwordEncoder;

    private final ExistsUserPort existsUserPort;
    private final RegisterUserPort registerUserPort;

    @Override
    public Long registerUser(final UserRegisterCommand command) {
        if (existsUserPort.isLeaveEmail(command.getEmail())) {
            throw new RainbowLetterException("account.leave", command.getEmail());
        }
        if (existsUserPort.existsUserByEmail(command.getEmail())) {
            throw new RainbowLetterException("exists.email", command.getEmail());
        }

        final User user = User.withoutId(
            command.getEmail(),
            passwordEncoder.encode(command.getPassword()),
            command.getPhoneNumber(),
            command.getRole(),
            command.getStatus(),
            command.getProvider(),
            command.getProviderId(),
            command.getLastLoggedIn(),
            timeHolder.currentTime(),
            command.getCreatedAt(),
            command.getUpdatedAt()
        );
        return registerUserPort.registerUser(user)
            .getId()
            .value();
    }

}
