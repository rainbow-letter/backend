package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.letter.application.port.in.dto.LetterResponse;
import java.util.UUID;

public interface GetLetterByShareLinkUseCase {

    LetterResponse getLetter(GetLetterByShareLinkQuery query);

    record GetLetterByShareLinkQuery(UUID shareLink) {

    }

}
