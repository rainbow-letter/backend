package com.rainbowletter.server.faq.application.port.in.dto;

import com.rainbowletter.server.faq.application.domain.model.Faq;
import java.time.LocalDateTime;
import java.util.List;

public record FaqsForAdminResponse(List<FaqForAdminResponse> faqs) {

    public static FaqsForAdminResponse from(final List<Faq> faqs) {
        return new FaqsForAdminResponse(
            faqs.stream()
                .map(FaqForAdminResponse::from)
                .toList()
        );
    }

    public record FaqForAdminResponse(
        Long id,
        String summary,
        String detail,
        boolean visibility,
        Long sequence,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {

        public static FaqForAdminResponse from(final Faq faq) {
            return new FaqForAdminResponse(
                faq.getId().value(),
                faq.getSummary(),
                faq.getDetail(),
                faq.isVisibility(),
                faq.getSequence(),
                faq.getCreatedAt(),
                faq.getUpdatedAt()
            );
        }

    }

}
