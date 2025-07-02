package com.rainbowletter.server.notification.application.port.in;

import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

public interface GetMailTemplateUseCase {

    String getTitle(GetMailTemplateQuery query);

    String getContent(GetMailTemplateQuery query);

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    class GetMailTemplateQuery {

        MailTemplateCode templateCode;
        String title;
        String receiver;
        List<Object> args;

        public static GetMailTemplateQuery titleQuery(
            final MailTemplateCode templateCode,
            final String title
        ) {
            return new GetMailTemplateQuery(templateCode, title, null, List.of());
        }

        public static GetMailTemplateQuery contentQuery(
            final MailTemplateCode templateCode,
            final String receiver,
            final List<Object> args
        ) {
            return new GetMailTemplateQuery(templateCode, null, receiver, args);
        }

    }

}
