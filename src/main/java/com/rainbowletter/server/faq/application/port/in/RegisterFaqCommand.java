package com.rainbowletter.server.faq.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.faq.application.port.in.validation.FaqSummary;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class RegisterFaqCommand {

    @FaqSummary
    String summary;

    @NotBlank(message = "FAQ 본문을 입력해주세요.")
    String detail;

    public RegisterFaqCommand(final String summary, final String detail) {
        this.summary = summary;
        this.detail = detail;
        validate(this);
    }

}
