package com.rainbowletter.server.letter.application.port.out;

import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;

import java.time.LocalDateTime;

public interface CountLetterPort {

    Long countByUserId(UserId userId);

    Long countByPetId(PetId petId);

    Integer getLastNumber(String email, PetId petId);

    Long countByPetIdAndEndDate(Long petId, LocalDateTime endDate);
}
