package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.port.in.UpdateFaqContentCommand;
import com.rainbowletter.server.faq.application.port.in.UpdateFaqContentUseCase;
import com.rainbowletter.server.faq.application.port.out.LoadFaqPort;
import com.rainbowletter.server.faq.application.port.out.UpdateFaqStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateFaqContentService implements UpdateFaqContentUseCase {

    private final LoadFaqPort loadFaqPort;
    private final UpdateFaqStatePort updateFaqStatePort;

    @Override
    public void updateContent(final UpdateFaqContentCommand command) {
        final Faq faq = loadFaqPort.loadFaq(command.getFaqId());
        faq.updateContent(command.getSummary(), command.getDetail());
        updateFaqStatePort.updateFaq(faq);
    }

}
