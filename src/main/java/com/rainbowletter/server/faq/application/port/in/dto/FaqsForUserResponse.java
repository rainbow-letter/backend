package com.rainbowletter.server.faq.application.port.in.dto;

import com.rainbowletter.server.faq.application.domain.model.Faq;
import java.util.List;

public record FaqsForUserResponse(List<FaqForUserResponse> faqs) {

    public static FaqsForUserResponse from(final List<Faq> faqs) {
        return new FaqsForUserResponse(
            faqs.stream()
                .map(FaqForUserResponse::from)
                .toList()
        );
    }

    public record FaqForUserResponse(Long id, String summary, String detail) {

        public static FaqForUserResponse from(final Faq faq) {
            return new FaqForUserResponse(faq.getId().value(), faq.getSummary(), faq.getDetail());
        }

    }

}
