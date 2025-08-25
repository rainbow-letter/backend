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
        return "[무지개편지] 선편지 도착 알림";
    }

    @Override
    public String failSubject(final Object... args) {
        return "[무지개편지]";
    }

    @Override
    public String content(final Object... args) {
        validateTemplateParameters(3, args);
        return """
            [무지개편지] 편지가 도착했어요!
            \s
            안녕하세요,
            %s %s님!
            %s에게서 먼저 편지가 왔어요 : )
            \s
            아이가 먼저 보낸 편지를 보러 가실까요?
            \s
            * 본 메시지는 고객님이 신청하신 '편지 받아보기'에 대한 안내 메세지입니다.
            """.formatted(args);
    }

    @Override
    public String failContent(final Object... args) {
        validateTemplateParameters(2, args);
        return """
            [무지개편지] 편지가 도착했어요!
            \s
            안녕하세요,
            %s %s님!
            %s에게서 먼저 편지가 왔어요 : )
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
