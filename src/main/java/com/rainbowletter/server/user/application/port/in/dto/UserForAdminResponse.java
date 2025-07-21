package com.rainbowletter.server.user.application.port.in.dto;

import java.time.LocalDateTime;

public record UserForAdminResponse(
    Long id,
    String email,
    String phoneNumber,
    LocalDateTime lastLoggedIn,
    LocalDateTime createdAt,
    boolean isPetInitiatedLetterEnabled
) {
}
