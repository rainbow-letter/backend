package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.pet.application.port.in.GetDashboardFromUserUseCase;
import com.rainbowletter.server.pet.application.port.in.dto.PetDashboardResponses;
import com.rainbowletter.server.pet.application.port.out.LoadPetDashboardPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetDashboardFromUserService implements GetDashboardFromUserUseCase {

    private final LoadPetDashboardPort loadPetDashboardPort;

    @Override
    public PetDashboardResponses getDashboardFromUser(final GetDashboardFromUserQuery query) {
        final var result = loadPetDashboardPort.loadPetDashboard(query.email());
        return PetDashboardResponses.from(result);
    }

}
