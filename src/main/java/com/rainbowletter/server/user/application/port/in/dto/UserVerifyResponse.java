package com.rainbowletter.server.user.application.port.in.dto;

public record UserVerifyResponse(String email, String role) {

    public static UserVerifyResponse of(final String email, final String role) {
        return new UserVerifyResponse(email, role);
    }

}
