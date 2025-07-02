package com.rainbowletter.server.letter.application.port.in;

public interface CreateLetterUseCase {

    Long createLetter(CreateLetterCommand command);

}
