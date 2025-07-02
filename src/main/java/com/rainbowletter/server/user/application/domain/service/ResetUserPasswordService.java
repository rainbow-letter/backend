package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.in.ResetUserPasswordUseCase;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class ResetUserPasswordService implements ResetUserPasswordUseCase {

    private final TimeHolder timeHolder;
    private final PasswordEncoder passwordEncoder;

    private final LoadUserPort loadUserPort;
    private final UpdateUserStatePort updateUserStatePort;

    @Override
    public void resetUserPassword(final ResetUserPasswordCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.getEmail());
        user.updatePassword(
            passwordEncoder.encode(command.getNewPassword()),
            timeHolder.currentTime()
        );
        updateUserStatePort.updateUser(user);
    }

}
