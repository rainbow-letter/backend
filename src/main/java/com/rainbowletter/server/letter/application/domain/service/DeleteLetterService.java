package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.in.DeleteLetterUseCase;
import com.rainbowletter.server.letter.application.port.out.DeleteLetterPort;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class DeleteLetterService implements DeleteLetterUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadLetterPort loadLetterPort;
    private final DeleteLetterPort deleteLetterPort;
    private final PublishDomainEventPort publishDomainEventPort;

    @Override
    public void deleteLetter(final DeleteLetterCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.email());
        final Letter letter = loadLetterPort.loadLetterByIdAndUserId(command.id(), user.getId());
        letter.delete();
        publishDomainEventPort.publish(letter);
        deleteLetterPort.delete(letter);
    }

}
