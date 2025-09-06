package com.rainbowletter.server.pet.application.port.in.dto;

import java.time.LocalDate;
import java.util.List;

public record PetForAdminResponse(
    Long id,
    String name,
    String owner,
    String species,
    List<String> personalities,
    LocalDate deathAnniversary
) {
}
