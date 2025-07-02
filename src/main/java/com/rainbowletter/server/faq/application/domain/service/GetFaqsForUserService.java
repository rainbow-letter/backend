package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.port.in.GetFaqsForUserUseCase;
import com.rainbowletter.server.faq.application.port.in.dto.FaqsForUserResponse;
import com.rainbowletter.server.faq.application.port.out.LoadFaqPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetFaqsForUserService implements GetFaqsForUserUseCase {

    private final LoadFaqPort loadFaqPort;

    @Override
    public FaqsForUserResponse getFaqsForUser() {
        final List<Faq> faqs = loadFaqPort.loadVisibilityFaqs();
        return FaqsForUserResponse.from(faqs);
    }

}
