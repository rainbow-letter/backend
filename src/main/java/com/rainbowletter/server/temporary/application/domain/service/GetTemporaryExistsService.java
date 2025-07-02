package com.rainbowletter.server.temporary.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.temporary.application.port.in.GetTemporaryExistsUseCase;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryExistsResponse;
import com.rainbowletter.server.temporary.application.port.out.ExistsTemporaryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetTemporaryExistsService implements GetTemporaryExistsUseCase {

    private final ExistsTemporaryPort existsTemporaryPort;

    @Override
    public TemporaryExistsResponse getExists(final GetTemporaryExistsQuery query) {
        return TemporaryExistsResponse.from(
            existsTemporaryPort.existsEmailAndPetId(query.email(), query.petId())
        );
    }

}
