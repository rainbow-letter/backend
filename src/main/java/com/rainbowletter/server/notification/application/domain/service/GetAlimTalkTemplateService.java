package com.rainbowletter.server.notification.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.template.AlimTalkTemplate;
import com.rainbowletter.server.notification.application.port.in.GetAlimTalkTemplateUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
class GetAlimTalkTemplateService implements GetAlimTalkTemplateUseCase {

    private final List<AlimTalkTemplate> alimTalkTemplates;

    @Override
    public String getSubject(final GetAlimTalkTemplateQuery query) {
        final AlimTalkTemplate alimTalkTemplate = findTemplateByCode(query.templateCode());
        return alimTalkTemplate.subject(query.args().toArray());
    }

    @Override
    public String failSubject(final GetAlimTalkTemplateQuery query) {
        final AlimTalkTemplate alimTalkTemplate = findTemplateByCode(query.templateCode());
        return alimTalkTemplate.failSubject(query.args().toArray());
    }

    @Override
    public String getContent(final GetAlimTalkTemplateQuery query) {
        final AlimTalkTemplate alimTalkTemplate = findTemplateByCode(query.templateCode());
        return alimTalkTemplate.content(query.args().toArray());
    }

    @Override
    public String failContent(final GetAlimTalkTemplateQuery query) {
        final AlimTalkTemplate alimTalkTemplate = findTemplateByCode(query.templateCode());
        return alimTalkTemplate.failContent(query.args().toArray());
    }

    @Override
    public List<AlimTalkButton> getButtons(final GetAlimTalkTemplateQuery query) {
        final AlimTalkTemplate alimTalkTemplate = findTemplateByCode(query.templateCode());
        return alimTalkTemplate.buttons(query.args().toArray());
    }

    private AlimTalkTemplate findTemplateByCode(final AlimTalkTemplateCode templateCode) {
        return alimTalkTemplates.stream()
            .filter(template -> template.support(templateCode))
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("지원하는 알림톡 템플릿을 찾을 수 없습니다."));
    }

}
