package com.rainbowletter.server.notification.application.port.out.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SendAlimTalkResponse(int result_code, String message) {

}
