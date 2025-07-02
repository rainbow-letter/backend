package com.rainbowletter.server.faq.application.port.out;

import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;

public interface DeleteFaqPort {

    void deleteFaq(final FaqId faqId);

}
