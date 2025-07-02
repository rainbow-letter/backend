package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.port.in.RegisterFaqCommand;
import com.rainbowletter.server.faq.application.port.in.RegisterFaqUseCase;
import com.rainbowletter.server.faq.application.port.out.SaveFaqPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class RegisterFaqService implements RegisterFaqUseCase {

    private final SaveFaqPort saveFaqPort;

    @Override
    public Long register(final RegisterFaqCommand command) {
        final Faq faq = Faq.withoutId(command.getSummary(), command.getDetail(), true);
        return saveFaqPort.saveFaq(faq)
            .getId()
            .value();
    }

}
