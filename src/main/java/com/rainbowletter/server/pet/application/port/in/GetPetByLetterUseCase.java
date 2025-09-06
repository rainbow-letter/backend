package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.port.in.dto.PetDetailResponse;
import java.util.UUID;

public interface GetPetByLetterUseCase {

    PetDetailResponse getPetByLetterId(GetPetByLetterIdQuery query);

    PetDetailResponse getPetByShareLink(GetPetByShareLinkQuery query);

    record GetPetByLetterIdQuery(LetterId letterId) {

    }

    record GetPetByShareLinkQuery(UUID shareLink) {

    }

}
