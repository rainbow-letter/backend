package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.port.in.SwitchFaqSequenceCommand;
import com.rainbowletter.server.faq.application.port.in.SwitchFaqSequenceUseCase;
import com.rainbowletter.server.faq.application.port.out.LoadFaqPort;
import com.rainbowletter.server.faq.application.port.out.UpdateFaqStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class SwitchFaqSequenceService implements SwitchFaqSequenceUseCase {

    private final LoadFaqPort loadFaqPort;
    private final UpdateFaqStatePort updateFaqStatePort;

    @Override
    public void switchSequence(final SwitchFaqSequenceCommand command) {
        final Faq sourceFaq = loadFaqPort.loadFaq(command.getSourceId());
        final Faq targetFaq = loadFaqPort.loadFaq(command.getTargetId());

        final Long tempSequence = sourceFaq.getSequence();
        sourceFaq.changeSequence(targetFaq.getSequence());
        targetFaq.changeSequence(tempSequence);

        updateFaqStatePort.updateFaq(sourceFaq);
        updateFaqStatePort.updateFaq(targetFaq);
    }

}
