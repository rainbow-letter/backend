package com.rainbowletter.server.petinitiatedletter.application.domain.model;

import lombok.Getter;

@Getter
public enum PetInitiatedLetterStatus {
    SCHEDULED, READY_TO_SEND, SENT
}
