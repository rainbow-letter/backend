package com.rainbowletter.server.user.application.port.in;

import com.rainbowletter.server.user.application.domain.model.User.UserId;
import com.rainbowletter.server.user.application.port.in.dto.UserInformationResponse;

public interface GetUserInformationUseCase {

    UserInformationResponse getUserInformation(GetUserInformationQuery query);

    UserInformationResponse getUserInformation(GetUserInformationByIdQuery query);

    record GetUserInformationQuery(String email) {

    }

    record GetUserInformationByIdQuery(UserId userId) {

    }

}
