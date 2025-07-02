package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Value;
import org.springframework.data.domain.Pageable;

@Value
public class GetLettersFromAdminQuery {

    LocalDateTime startDateTime;

    LocalDateTime endDateTime;

    @Nullable
    ReplyStatus status;

    @Nullable
    String email;

    @Nullable
    Boolean inspect;

    Pageable pageable;

    public GetLettersFromAdminQuery(
        final LocalDate startDate,
        final LocalDate endDate,
        @Nullable final ReplyStatus status,
        @Nullable final String email,
        @Nullable final Boolean inspect,
        final Pageable pageable
    ) {
        this.startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        this.endDateTime = LocalDateTime.of(endDate, LocalTime.MAX).withNano(0);
        this.status = status;
        this.email = email;
        this.inspect = inspect;
        this.pageable = pageable;
    }

}
