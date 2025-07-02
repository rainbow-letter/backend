package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.port.in.ChangeFaqVisibilityUseCase;
import com.rainbowletter.server.faq.application.port.out.LoadFaqPort;
import com.rainbowletter.server.faq.application.port.out.UpdateFaqStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class ChangeFaqVisibilityService implements ChangeFaqVisibilityUseCase {

    private final LoadFaqPort loadFaqPort;
    private final UpdateFaqStatePort updateFaqStatePort;

    @Override
    public void changeVisibility(final ChangeFaqVisibilityCommand command) {
        final Faq faq = loadFaqPort.loadFaq(command.faqId());
        faq.changeVisibility();
        updateFaqStatePort.updateFaq(faq);
    }

}
