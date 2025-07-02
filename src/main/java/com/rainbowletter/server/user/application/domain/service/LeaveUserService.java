package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.in.LeaveUserUseCase;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
@Transactional
@Slf4j
class LeaveUserService implements LeaveUserUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserStatePort updateUserStatePort;

    @Override
    public void leaveUser(final LeaveUserCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.email());
        log.info("[회원 탈퇴 요청] email={}, userId={}, 탈퇴 시각={}",
            user.getEmail(), user.getId(), LocalDateTime.now());
        user.leave();
        updateUserStatePort.updateUser(user);
    }

}
