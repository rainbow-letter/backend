package com.rainbowletter.server.notification.application.domain.model.alimtalk.template;

import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PetInitiatedLetterAlimTalkTemplate extends AbstractAlimTalkTemplate {

    PetInitiatedLetterAlimTalkTemplate() {
        super(AlimTalkTemplateCode.PET_INITIATED_LETTER);
    }

    @Override
    public String subject(final Object... args) {
        return "[무지개편지] 편지 도착 알림";
    }

    @Override
    public String failSubject(final Object... args) {
        return "[무지개편지]";
    }

    @Override
    public String content(final Object... args) {
        validateTemplateParameters(4, args);
        return """
            [무지개편지] 편지 도착 알림
            \s
            안녕하세요,
            %s %s님!
            %s, %s에게서 편지가 도착했어요! : )
            \s
            아래 '편지 보러 가기' 버튼을 누르시면 편지함으로 이동합니다.
            """.formatted(args);
    }

    @Override
    public String failContent(final Object... args) {
        validateTemplateParameters(2, args);
        return """
            [무지개편지]
            \s
            %s, %s에게서 편지가 도착했어요! : )
            \s
            편지함에서 확인해주세요!
            """.formatted(args);
    }

    @Override
    public List<AlimTalkButton> buttons(final Object... args) {
        validateTemplateParameters(1, args);
        final String shareLink = String.valueOf(args[0]);
        final var button1 = AlimTalkButton.createWebLink("편지 보러 가기", shareLink);
        return List.of(button1);
    }
}
