package com.rainbowletter.server.user.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;

public record UserPetLetterSettingRequest(
    @NotNull
    boolean enabled
) {
}
