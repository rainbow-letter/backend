package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.in.GetUserInformationUseCase;
import com.rainbowletter.server.user.application.port.in.dto.UserInformationResponse;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetUserInformationService implements GetUserInformationUseCase {

    private final LoadUserPort loadUserPort;

    @Override
    public UserInformationResponse getUserInformation(final GetUserInformationQuery query) {
        final User user = loadUserPort.loadUserByEmail(query.email());
        return UserInformationResponse.from(user);
    }

    @Override
    public UserInformationResponse getUserInformation(final GetUserInformationByIdQuery query) {
        final User user = loadUserPort.loadUserById(query.userId());
        return UserInformationResponse.from(user);
    }

}
