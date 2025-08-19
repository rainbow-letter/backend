package com.rainbowletter.server.pet.application.port.out.dto;

import java.time.LocalDate;

public record PetDashboardResponse(
    Long id,
    String name,
    Long letterCount,
    String image,
    LocalDate deathAnniversary
) {

}
