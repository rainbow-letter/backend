package com.rainbowletter.server.faq.application.port.in;

import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;

public interface ChangeFaqVisibilityUseCase {

    void changeVisibility(ChangeFaqVisibilityCommand command);

    record ChangeFaqVisibilityCommand(FaqId faqId) {

    }

}
