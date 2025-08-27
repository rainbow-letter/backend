package com.rainbowletter.server.petinitiatedletter.application.port.in.dto;

public record PetInitiatedLetterStats(
    Long totalLetters,
    Long scheduled,
    Long readyToSend,
    Long sent
) {
}