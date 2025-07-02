package com.rainbowletter.server.temporary.application.port.in.dto;

public record TemporaryExistsResponse(boolean exists) {

    public static TemporaryExistsResponse from(final boolean exists) {
        return new TemporaryExistsResponse(exists);
    }

}
