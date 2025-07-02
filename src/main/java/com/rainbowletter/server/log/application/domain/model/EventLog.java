package com.rainbowletter.server.log.application.domain.model;

public record EventLog(
    EventLogId id,
    Long resource,
    Long userId,
    String category,
    String event,
    String message,
    EventLogStatus status
) {

    public static EventLog withoutId(
        final Long resource,
        final Long userId,
        final String category,
        final String event,
        final String message,
        final EventLogStatus status
    ) {
        return new EventLog(null, resource, userId, category, event, message, status);
    }

    public enum EventLogStatus {
        SUCCESS,
        FAIL,
    }

    public record EventLogId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
