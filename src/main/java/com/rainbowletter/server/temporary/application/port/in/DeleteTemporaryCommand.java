package com.rainbowletter.server.temporary.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;
import static com.rainbowletter.server.pet.application.domain.model.Pet.PetId;

import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class DeleteTemporaryCommand {

    String email;

    @NotNull(message = "임시저장 ID는 NULL 값을 허용하지 않습니다.")
    TemporaryId id;

    @NotNull(message = "반려동물 ID는 NULL 값을 허용하지 않습니다.")
    PetId petId;

    public DeleteTemporaryCommand(
        final String email,
        final TemporaryId id,
        final PetId petId
    ) {
        this.email = email;
        this.id = id;
        this.petId = petId;
        validate(this);
    }

}
