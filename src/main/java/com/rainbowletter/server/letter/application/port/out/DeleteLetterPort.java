package com.rainbowletter.server.letter.application.port.out;

import com.rainbowletter.server.letter.application.domain.model.Letter;
import java.util.List;

public interface DeleteLetterPort {

    void delete(Letter letter);

    void deleteAll(List<Letter> letters);

}
