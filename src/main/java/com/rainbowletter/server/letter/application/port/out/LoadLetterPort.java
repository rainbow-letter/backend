package com.rainbowletter.server.letter.application.port.out;

import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.letter.adapter.out.dto.RecentLetterSummary;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleResponse;
import com.rainbowletter.server.letter.application.port.out.dto.LetterBoxResponse;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.slack.adapter.out.dto.LetterStats;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LoadLetterPort {

    Letter loadLetterById(LetterId id);

    Letter loadLetterByIdAndUserId(LetterId id, UserId userId);

    Letter loadLetterByEmailAndId(String email, LetterId id);

    Letter loadLetterByShareLink(UUID shareLink);

    List<Letter> loadLettersByPetId(PetId petId);

    List<LetterBoxResponse> loadLetterBox(
        String email,
        PetId petId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
    );

    List<LetterAdminRecentResponse> loadRecentLettersByUserId(UserId userId);

    Page<LetterAdminPageResponse> loadLettersFromAdmin(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        ReplyStatus status,
        String email,
        Boolean inspect,
        Pageable pageable
    );

    List<RecentLetterSummary> loadRecentLettersByPetId(PetId petId, Long letterId, LocalDateTime currentCreatedAt);

    LetterStats getLetterReportByCreatedAtBetween(LocalDateTime letterStartTime, LocalDateTime letterEndTime);

    List<LetterSimpleResponse> findByPetId(Long petId, RetrieveLetterRequest query);
}
