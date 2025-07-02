package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.port.in.DeleteFaqUseCase;
import com.rainbowletter.server.faq.application.port.out.DeleteFaqPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class DeleteFaqService implements DeleteFaqUseCase {

    private final DeleteFaqPort deleteFaqPort;

    @Override
    public void delete(final DeleteFaqCommand command) {
        deleteFaqPort.deleteFaq(command.faqId());
    }

}
