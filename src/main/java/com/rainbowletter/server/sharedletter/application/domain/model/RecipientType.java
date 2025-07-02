package com.rainbowletter.server.sharedletter.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecipientType {
    PET(0),
    OWNER(1);

    private final int code;
}