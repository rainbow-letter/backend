package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;

public interface DeleteLetterUseCase {

    void deleteLetter(DeleteLetterCommand command);

    record DeleteLetterCommand(String email, LetterId id) {

    }

}
