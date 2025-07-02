package com.rainbowletter.server.faq.adapter.in.web.dto;

import com.rainbowletter.server.faq.application.port.in.validation.FaqSummary;
import jakarta.validation.constraints.NotBlank;

public record UpdateFaqContentRequest(
    @FaqSummary
    String summary,

    @NotBlank(message = "항목을 입력해주세요.")
    String detail
) {

}
