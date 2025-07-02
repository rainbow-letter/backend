package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.letter.application.port.in.dto.LetterBoxResponses;

public interface GetLetterBoxUseCase {

    LetterBoxResponses getLetterBox(GetLetterBoxQuery query);

}
