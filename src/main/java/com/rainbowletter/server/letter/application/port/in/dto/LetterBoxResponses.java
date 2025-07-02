package com.rainbowletter.server.letter.application.port.in.dto;

import com.rainbowletter.server.letter.application.port.out.dto.LetterBoxResponse;
import java.util.List;

public record LetterBoxResponses(List<LetterBoxResponse> letters) {

    public static LetterBoxResponses from(final List<LetterBoxResponse> letterBoxResponses) {
        return new LetterBoxResponses(letterBoxResponses);
    }

}
