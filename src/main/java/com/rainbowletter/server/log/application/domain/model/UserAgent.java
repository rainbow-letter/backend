package com.rainbowletter.server.log.application.domain.model;

import lombok.Builder;

@Builder
public record UserAgent(
    String device,
    String deviceName,
    String os,
    String osName,
    String osVersion,
    String agent,
    String agentName,
    String agentVersion
) {

}
