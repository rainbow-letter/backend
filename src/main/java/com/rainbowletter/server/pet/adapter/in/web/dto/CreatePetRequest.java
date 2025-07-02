package com.rainbowletter.server.pet.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.Set;

public record CreatePetRequest(
    String name,
    String species,
    String owner,
    Set<String> personalities,
    LocalDate deathAnniversary,
    String image
) {

}
