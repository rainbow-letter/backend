package com.rainbowletter.server.temporary.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.util.UuidHolder;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.port.in.ChangeTemporarySessionCommand;
import com.rainbowletter.server.temporary.application.port.in.ChangeTemporarySessionUseCase;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporarySessionResponse;
import com.rainbowletter.server.temporary.application.port.out.LoadTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.UpdateTemporaryStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class ChangeTemporarySessionService implements ChangeTemporarySessionUseCase {

    private final UuidHolder uuidHolder;
    private final LoadTemporaryPort loadTemporaryPort;
    private final UpdateTemporaryStatePort updateTemporaryStatePort;

    @Override
    public TemporarySessionResponse changeSession(final ChangeTemporarySessionCommand command) {
        final Temporary temporary =
            loadTemporaryPort.loadByEmailAndId(command.getEmail(), command.getId());
        temporary.changeSessionId(uuidHolder);
        return TemporarySessionResponse.from(
            updateTemporaryStatePort.updateTemporary(temporary)
        );
    }

}
