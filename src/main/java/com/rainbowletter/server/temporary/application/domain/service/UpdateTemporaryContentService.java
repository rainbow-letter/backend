package com.rainbowletter.server.temporary.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.port.in.UpdateTemporaryContentCommand;
import com.rainbowletter.server.temporary.application.port.in.UpdateTemporaryContentUseCase;
import com.rainbowletter.server.temporary.application.port.out.LoadTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.UpdateTemporaryStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateTemporaryContentService implements UpdateTemporaryContentUseCase {

    private final LoadTemporaryPort loadTemporaryPort;
    private final UpdateTemporaryStatePort updateTemporaryStatePort;

    @Override
    public void updateContent(final UpdateTemporaryContentCommand command) {
        final Temporary temporary =
            loadTemporaryPort.loadByEmailAndId(command.getEmail(), command.getId());
        temporary.updateContent(command.getContent());
        updateTemporaryStatePort.updateTemporary(temporary);
    }

}
