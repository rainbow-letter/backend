package com.rainbowletter.server.faq.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;
import static com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;

import com.rainbowletter.server.faq.application.port.in.validation.FaqSummary;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class UpdateFaqContentCommand {

    @NotNull
    FaqId faqId;

    @FaqSummary
    String summary;

    @NotBlank(message = "수정할 FAQ 본문을 입력해주세요.")
    String detail;

    public UpdateFaqContentCommand(final FaqId faqId, final String summary, final String detail) {
        this.faqId = faqId;
        this.summary = summary;
        this.detail = detail;
        validate(this);
    }

}
