package com.rainbowletter.server.user.adapter.in.web.dto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public record PetSelectionRequest(
    @NotNull
    Long petId
) {
}
