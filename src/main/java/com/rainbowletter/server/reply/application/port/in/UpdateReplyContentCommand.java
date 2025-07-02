package com.rainbowletter.server.reply.application.port.in;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import static com.rainbowletter.server.common.util.Validation.validate;

import com.rainbowletter.server.letter.application.port.in.validation.LetterContent;
import com.rainbowletter.server.letter.application.port.in.validation.LetterSummary;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class UpdateReplyContentCommand {

    ReplyId replyId;

    @NotNull
    PromptType promptType;

    @LetterSummary
    String summary;

    @LetterContent
    String content;

    public UpdateReplyContentCommand(
        final ReplyId replyId,
        final PromptType promptType,
        final String summary,
        final String content
    ) {
        this.replyId = replyId;
        this.promptType = promptType;
        this.summary = summary;
        this.content = content;
        validate(this);
    }

}
