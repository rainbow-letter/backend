package com.rainbowletter.server.reply.application.port.out;

import com.rainbowletter.server.ai.application.domain.model.Parameter.FirstLetter;
import com.rainbowletter.server.ai.application.domain.model.Parameter.LetterCount;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class GenerateReplyCommand {

    Pet pet;
    Letter letter;
    LetterCount letterCount;
    FirstLetter isFirstLetter;

    public GenerateReplyCommand(
        final Pet pet,
        final Letter letter,
        final LetterCount letterCount,
        final FirstLetter isFirstLetter
    ) {
        this.pet = pet;
        this.letter = letter;
        this.letterCount = letterCount;
        this.isFirstLetter = isFirstLetter;
    }

}
