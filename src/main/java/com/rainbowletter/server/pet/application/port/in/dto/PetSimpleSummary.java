package com.rainbowletter.server.pet.application.port.in.dto;

public record PetSimpleSummary(
    Long id,
    String name,
    String image
) {
}