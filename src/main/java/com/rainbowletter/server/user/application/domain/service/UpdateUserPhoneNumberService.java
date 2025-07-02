package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.in.UpdateUserPhoneNumberUseCase;
import com.rainbowletter.server.user.application.port.out.ExistsUserPort;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateUserPhoneNumberService implements UpdateUserPhoneNumberUseCase {

    private final LoadUserPort loadUserPort;
    private final ExistsUserPort existsUserPort;
    private final UpdateUserStatePort updateUserStatePort;

    @Override
    public void updateUserPhoneNumber(final UpdateUserPhoneNumberCommand command) {
        final String phoneNumber = command.getPhoneNumber();
        if (phoneNumber != null && existsUserPort.existsUserByPhoneNumber(phoneNumber)) {
            throw new RainbowLetterException("exists.phone-number", phoneNumber);
        }

        final User user = loadUserPort.loadUserByEmail(command.getEmail());
        user.updatePhoneNumber(phoneNumber);
        updateUserStatePort.updateUser(user);
    }

}
