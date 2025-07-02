package com.rainbowletter.server.log.application.domain.model;

public record UserAgentLog(
    UserAgentLogId id,
    String event,
    String device,
    String deviceName,
    String os,
    String osName,
    String osVersion,
    String agent,
    String agentName,
    String agentVersion
) {

    public static UserAgentLog withId(
        final UserAgentLogId id,
        final String event,
        final UserAgent userAgent
    ) {
        return new UserAgentLog(
            id,
            event,
            userAgent.device(),
            userAgent.deviceName(),
            userAgent.os(),
            userAgent.osName(),
            userAgent.osVersion(),
            userAgent.agent(),
            userAgent.agentName(),
            userAgent.agentVersion()
        );
    }

    public static UserAgentLog withoutId(final String event, final UserAgent userAgent) {
        return new UserAgentLog(
            null,
            event,
            userAgent.device(),
            userAgent.deviceName(),
            userAgent.os(),
            userAgent.osName(),
            userAgent.osVersion(),
            userAgent.agent(),
            userAgent.agentName(),
            userAgent.agentVersion()
        );
    }

    public record UserAgentLogId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
