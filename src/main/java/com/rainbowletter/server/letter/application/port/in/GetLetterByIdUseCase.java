package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.in.dto.LetterResponse;

public interface GetLetterByIdUseCase {

    LetterResponse getLetter(GetLetterByIdQuery query);

    record GetLetterByIdQuery(LetterId id) {

    }

}
