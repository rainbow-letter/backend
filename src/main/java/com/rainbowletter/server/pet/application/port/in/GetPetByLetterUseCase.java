package com.rainbowletter.server.pet.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.port.in.dto.PetExcludeFavoriteResponse;
import java.util.UUID;

public interface GetPetByLetterUseCase {

    PetExcludeFavoriteResponse getPetByLetterId(GetPetByLetterIdQuery query);

    PetExcludeFavoriteResponse getPetByShareLink(GetPetByShareLinkQuery query);

    record GetPetByLetterIdQuery(LetterId letterId) {

    }

    record GetPetByShareLinkQuery(UUID shareLink) {

    }

}
