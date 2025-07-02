package com.rainbowletter.server.temporary.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.port.in.DeleteTemporaryCommand;
import com.rainbowletter.server.temporary.application.port.in.DeleteTemporaryUseCase;
import com.rainbowletter.server.temporary.application.port.out.LoadTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.UpdateTemporaryStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class DeleteTemporaryService implements DeleteTemporaryUseCase {

    private final LoadTemporaryPort loadTemporaryPort;
    private final UpdateTemporaryStatePort updateTemporaryStatePort;

    @Override
    public void deleteTemporary(final DeleteTemporaryCommand command) {
        final Temporary temporary =
            loadTemporaryPort.loadByEmailAndId(command.getEmail(), command.getId());
        temporary.delete();
        updateTemporaryStatePort.updateTemporary(temporary);
    }

}
