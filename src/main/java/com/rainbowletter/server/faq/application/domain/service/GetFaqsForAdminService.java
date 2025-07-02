package com.rainbowletter.server.faq.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.port.in.GetFaqsForAdminUseCase;
import com.rainbowletter.server.faq.application.port.in.dto.FaqsForAdminResponse;
import com.rainbowletter.server.faq.application.port.out.LoadFaqPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetFaqsForAdminService implements GetFaqsForAdminUseCase {

    private final LoadFaqPort loadFaqPort;

    @Override
    public FaqsForAdminResponse getFaqsForAdmin() {
        final List<Faq> faqs = loadFaqPort.loadFaqs();
        return FaqsForAdminResponse.from(faqs);
    }

}
