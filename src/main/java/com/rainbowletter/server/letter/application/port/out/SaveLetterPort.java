package com.rainbowletter.server.letter.application.port.out;

import com.rainbowletter.server.letter.application.domain.model.Letter;

public interface SaveLetterPort {

    Letter save(Letter letter);

}
