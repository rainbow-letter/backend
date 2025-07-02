package com.rainbowletter.server.faq.application.port.in;

import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;

public interface DeleteFaqUseCase {

    void delete(DeleteFaqCommand command);

    record DeleteFaqCommand(FaqId faqId) {

    }

}
