package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.in.FindUserPasswordUseCase;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class FindUserPasswordService implements FindUserPasswordUseCase {

    private final LoadUserPort loadUserPort;
    private final PublishDomainEventPort publishDomainEventPort;

    @Override
    public void findUserPassword(final FindUserPasswordQuery query) {
        final User user = loadUserPort.loadUserByEmail(query.getEmail());
        user.findPassword();
        publishDomainEventPort.publish(user);
    }

}
