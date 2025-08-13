package com.rainbowletter.server.notification.application.domain.model.mail;

import com.rainbowletter.server.common.config.ClientConfig;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import static com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode.REPLY;

@Component
class ReplyReceiveTemplate extends AbstractSurveyMailTemplate {

    ReplyReceiveTemplate(
        final ClientConfig clientConfig,
        final MessageSource messageSource,
        final ISpringTemplateEngine templateEngine
    ) {
        super(REPLY, clientConfig, messageSource, templateEngine);
    }

    @Override
    protected String getTemplateName() {
        return "receive-reply";
    }

}
