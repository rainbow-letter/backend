package com.rainbowletter.server.notification.application.domain.model.mail;

public interface MailTemplate {

    boolean support(MailTemplateCode templateCode);

    String getContent(String receiver, Object... args);

    default String getTitle(final String title) {
        return "[무지개 편지] %s".formatted(title);
    }

}
