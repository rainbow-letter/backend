package com.rainbowletter.server.log.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.log.application.domain.model.UserAgent;
import com.rainbowletter.server.log.application.port.out.AnalyzeUserAgentPort;
import lombok.RequiredArgsConstructor;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

@InfraAdapter
@RequiredArgsConstructor
class AnalyzeUserAgentAdapter implements AnalyzeUserAgentPort {

    private final UserAgentAnalyzer userAgentAnalyzer;

    @Override
    public UserAgent analyzeUserAgent(final String userAgent) {
        final var parsedUserAgent = userAgentAnalyzer.parse(userAgent);
        return UserAgent.builder()
            .device(parsedUserAgent.getValue("DeviceClass"))
            .deviceName(parsedUserAgent.getValue("DeviceName"))
            .os(parsedUserAgent.getValue("OperatingSystemClass"))
            .osName(parsedUserAgent.getValue("OperatingSystemName"))
            .osVersion(parsedUserAgent.getValue("OperatingSystemVersion"))
            .agent(parsedUserAgent.getValue("AgentClass"))
            .agentName(parsedUserAgent.getValue("AgentName"))
            .agentVersion(parsedUserAgent.getValue("AgentVersion"))
            .build();
    }

}
