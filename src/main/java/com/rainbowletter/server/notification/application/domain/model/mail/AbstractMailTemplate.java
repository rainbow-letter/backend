package com.rainbowletter.server.notification.application.domain.model.mail;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
abstract class AbstractMailTemplate implements MailTemplate {

    private final MailTemplateCode templateCode;

    @Override
    public boolean support(final MailTemplateCode templateCode) {
        return this.templateCode.equals(templateCode);
    }

    protected void validateTemplateParameters(
        final int length,
        final Object... args
    ) {
        if (args.length != length) {
            throw new RainbowLetterException("email.template.required.parameters", length);
        }
    }

}
