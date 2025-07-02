package com.rainbowletter.server.notification.application.domain.model.alimtalk.template;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
abstract class AbstractAlimTalkTemplate implements AlimTalkTemplate {

    private final AlimTalkTemplateCode templateCode;

    @Override
    public boolean support(final AlimTalkTemplateCode templateCode) {
        return this.templateCode.equals(templateCode);
    }

    protected void validateTemplateParameters(
        final int length,
        final Object... args
    ) {
        if (args.length != length) {
            throw new RainbowLetterException("alimtalk.parameter.size", length);
        }
    }

}
