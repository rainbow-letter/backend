package com.rainbowletter.server.notification.application.domain.model.mail;

import com.rainbowletter.server.common.config.ClientConfig;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import static com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode.PET_INITIATED_LETTER;

@Component
class PetInitiatedLetterTemplate extends AbstractSurveyMailTemplate {

    PetInitiatedLetterTemplate(
        ClientConfig clientConfig,
        MessageSource messageSource,
        ISpringTemplateEngine templateEngine
    ) {
        super(PET_INITIATED_LETTER, clientConfig, messageSource, templateEngine);
    }

    @Override
    protected String getTemplateName() {
        return "receive-pet-initiated-letter";
    }

}
