package com.rainbowletter.server.letter.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.letter.application.port.in.validation.LetterContent;
import com.rainbowletter.server.letter.application.port.in.validation.LetterSummary;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class CreateLetterCommand {

    String email;

    @NotNull(message = "반려동물 ID는 NULL 값을 허용하지 않습니다.")
    PetId petId;

    @LetterSummary
    String summary;

    @LetterContent
    String content;

    @Nullable
    String image;

    public CreateLetterCommand(
        final String email,
        final PetId petId,
        final String summary,
        final String content,
        @Nullable final String image
    ) {
        this.email = email;
        this.petId = petId;
        this.summary = summary;
        this.content = content;
        this.image = image;
        validate(this);
    }

}
