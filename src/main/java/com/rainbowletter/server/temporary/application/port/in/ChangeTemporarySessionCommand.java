package com.rainbowletter.server.temporary.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class ChangeTemporarySessionCommand {

    String email;

    @NotNull(message = "임시저장 ID는 NULL 값을 허용하지 않습니다.")
    TemporaryId id;

    public ChangeTemporarySessionCommand(
        final String email,
        final TemporaryId id
    ) {
        this.email = email;
        this.id = id;
        validate(this);
    }

}
