package com.rainbowletter.server.faq.application.port.in;

import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class SwitchFaqSequenceCommand {

    @NotNull
    FaqId sourceId;

    @NotNull
    FaqId targetId;

    public SwitchFaqSequenceCommand(final FaqId sourceId, final FaqId targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        validate(this);
    }

}
