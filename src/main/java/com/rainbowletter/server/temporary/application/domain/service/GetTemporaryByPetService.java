package com.rainbowletter.server.temporary.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.temporary.application.port.in.GetTemporaryByPetUseCase;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryResponse;
import com.rainbowletter.server.temporary.application.port.out.LoadTemporaryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetTemporaryByPetService implements GetTemporaryByPetUseCase {

    private final LoadTemporaryPort loadTemporaryPort;

    @Override
    public TemporaryResponse getTemporaryByPet(final GetTemporaryByPetQuery query) {
        return TemporaryResponse.from(
            loadTemporaryPort.loadByEmailAndPetId(query.email(), query.petId())
        );
    }

}
