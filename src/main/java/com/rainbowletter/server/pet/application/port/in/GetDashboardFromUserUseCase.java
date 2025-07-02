package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.pet.application.port.in.dto.PetDashboardResponses;

public interface GetDashboardFromUserUseCase {

    PetDashboardResponses getDashboardFromUser(GetDashboardFromUserQuery query);

    record GetDashboardFromUserQuery(String email) {

    }

}
