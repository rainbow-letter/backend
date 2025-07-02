package com.rainbowletter.server.pet.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.pet.application.port.in.validation.PetName;
import com.rainbowletter.server.pet.application.port.in.validation.PetOwner;
import com.rainbowletter.server.pet.application.port.in.validation.PetPersonalities;
import com.rainbowletter.server.pet.application.port.in.validation.PetPersonality;
import com.rainbowletter.server.pet.application.port.in.validation.PetSpecies;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Value;

@Value
public class CreatePetCommand {

    String email;

    @PetName
    String name;

    @PetSpecies
    String species;

    @PetOwner
    String owner;

    @PetPersonalities
    List<@PetPersonality String> personalities;

    @Nullable
    @PastOrPresent(message = "날짜가 과거가 아니거나, 형식이 잘못되었습니다.")
    LocalDate deathAnniversary;

    @Nullable
    String image;

    public CreatePetCommand(
        final String email,
        final String name,
        final String species,
        final String owner,
        final Set<String> personalities,
        @Nullable final LocalDate deathAnniversary,
        @Nullable final String image
    ) {
        this.email = email;
        this.name = name;
        this.species = species;
        this.owner = owner;
        this.personalities = personalities.stream().toList();
        this.deathAnniversary = deathAnniversary;
        this.image = image;
        validate(this);
    }

}
