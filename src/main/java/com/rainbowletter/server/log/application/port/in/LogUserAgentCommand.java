package com.rainbowletter.server.log.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class LogUserAgentCommand {

    @NotNull(message = "USER_AGENT 값이 비어있습니다.")
    String userAgent;

    @NotBlank(message = "이벤트를 입력해주세요.")
    String event;

    public LogUserAgentCommand(final String userAgent, final String event) {
        this.userAgent = userAgent;
        this.event = event;
        validate(this);
    }

}
