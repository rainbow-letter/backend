package com.rainbowletter.server.notification.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplate;
import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode;
import com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
class GetMailTemplateService implements GetMailTemplateUseCase {

    private final List<MailTemplate> mailTemplates;

    @Override
    public String getTitle(final GetMailTemplateQuery query) {
        final MailTemplate mailTemplate = findTemplateByCode(query.getTemplateCode());
        return mailTemplate.getTitle(query.getTitle());
    }

    @Override
    public String getContent(final GetMailTemplateQuery query) {
        final MailTemplate mailTemplate = findTemplateByCode(query.getTemplateCode());
        return mailTemplate.getContent(query.getReceiver(), query.getArgs().toArray());
    }

    private MailTemplate findTemplateByCode(final MailTemplateCode templateCode) {
        return mailTemplates.stream()
            .filter(template -> template.support(templateCode))
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("지원하는 메일 템플릿을 찾을 수 없습니다."));
    }

}
