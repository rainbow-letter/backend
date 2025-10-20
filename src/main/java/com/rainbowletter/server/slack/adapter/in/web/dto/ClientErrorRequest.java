package com.rainbowletter.server.slack.adapter.in.web.dto;

public record ClientErrorRequest(
    String message,
    String url
) {
}
