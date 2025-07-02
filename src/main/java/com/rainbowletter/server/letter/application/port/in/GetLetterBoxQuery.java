package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Value;

@Value
public class GetLetterBoxQuery {

    String email;
    PetId petId;

    @Nullable
    LocalDateTime startDateTime;

    @Nullable
    LocalDateTime endDateTime;

    public GetLetterBoxQuery(
        final String email,
        final PetId petId,
        @Nullable final LocalDate startDate,
        @Nullable final LocalDate endDate
    ) {
        LocalDateTime tempStartDateTime = null;
        LocalDateTime tempEndDateTime = null;

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            tempStartDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            tempEndDateTime = LocalDateTime.of(endDate, LocalTime.MAX).withNano(0);
        }
        this.email = email;
        this.petId = petId;
        this.startDateTime = tempStartDateTime;
        this.endDateTime = tempEndDateTime;
    }

}
