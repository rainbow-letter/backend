package com.rainbowletter.server.pet.application.port.out;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.List;
import java.util.UUID;

public interface LoadPetPort {

    Pet loadPetByIdAndUserId(PetId petId, UserId userId);

    Pet loadPetByLetterId(LetterId letterId);

    Pet loadPetByShareLink(UUID shareLink);

    List<Pet> loadPetsByUserId(UserId userId);

    PetSummary findPetSummaryById(Long petId, Long userId);
}
