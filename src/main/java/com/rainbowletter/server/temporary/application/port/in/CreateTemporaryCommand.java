package com.rainbowletter.server.temporary.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;
import static com.rainbowletter.server.pet.application.domain.model.Pet.PetId;

import com.rainbowletter.server.temporary.adapter.in.web.validation.TemporaryContent;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class CreateTemporaryCommand {

    String email;

    @NotNull(message = "반려동물 ID는 NULL 값을 허용하지 않습니다.")
    PetId petId;

    @TemporaryContent
    String content;

    public CreateTemporaryCommand(
        final String email,
        final PetId petId,
        final String content
    ) {
        this.email = email;
        this.petId = petId;
        this.content = content;
        validate(this);
    }

}
