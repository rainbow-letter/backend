package com.rainbowletter.server.notification.application.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification {

    private final NotificationId id;

    private String title;
    private String content;
    private String receiver;
    private String sender;
    private NotificationType type;
    private int code;
    private String statusMessage;

    public static Notification withoutId(
        final String title,
        final String content,
        final String receiver,
        final String sender,
        final NotificationType type,
        final int code,
        final String statusMessage
    ) {
        return new Notification(null, title, content, receiver, sender, type, code, statusMessage);
    }

    public enum NotificationType {
        SMS, ALIM_TALK, MAIL,
    }

    public record NotificationId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
