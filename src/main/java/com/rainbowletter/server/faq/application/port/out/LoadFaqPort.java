package com.rainbowletter.server.faq.application.port.out;

import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import java.util.List;

public interface LoadFaqPort {

    Faq loadFaq(FaqId faqId);

    List<Faq> loadFaqs();

    List<Faq> loadVisibilityFaqs();

}
