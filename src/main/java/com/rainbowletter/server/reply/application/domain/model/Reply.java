package com.rainbowletter.server.reply.application.domain.model;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.application.domain.model.AggregateRoot;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reply extends AggregateRoot {

    private final ReplyId id;
    private final PetId petId;
    private final LetterId letterId;

    private String summary;
    private String content;
    private String promptA;
    private String promptB;
    private PromptType promptType;
    private boolean inspection;
    private LocalDateTime inspectionTime;
    private ReplyStatus status;
    private LocalDateTime submitTime;
    private ReplyReadStatus readStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @SuppressWarnings("java:S107")
    public static Reply withId(
        final ReplyId id,
        final PetId petId,
        final LetterId letterId,
        final String summary,
        final String content,
        final String promptA,
        final String promptB,
        final PromptType promptType,
        final boolean inspection,
        final LocalDateTime inspectionTime,
        final ReplyStatus status,
        final LocalDateTime submitTime,
        final ReplyReadStatus readStatus,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Reply(
            id,
            petId,
            letterId,
            summary,
            content,
            promptA,
            promptB,
            promptType,
            inspection,
            inspectionTime,
            status,
            submitTime,
            readStatus,
            createdAt,
            updatedAt
        );
    }

    @SuppressWarnings("java:S107")
    public static Reply withoutId(
        final PetId petId,
        final LetterId letterId,
        final String summary,
        final String content,
        final String promptA,
        final String promptB,
        final PromptType promptType,
        final boolean inspection,
        final LocalDateTime inspectionTime,
        final ReplyStatus status,
        final LocalDateTime submitTime,
        final ReplyReadStatus readStatus,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Reply(
            null,
            petId,
            letterId,
            summary,
            content,
            promptA,
            promptB,
            promptType,
            inspection,
            inspectionTime,
            status,
            submitTime,
            readStatus,
            createdAt,
            updatedAt
        );
    }

    public void update(final PromptType promptType, final String summary, final String content) {
        this.promptType = promptType;
        this.summary = summary;
        this.content = content;

        if (promptType == PromptType.A) {
            this.promptA = content;
        } else if (promptType == PromptType.B) {
            this.promptB = content;
        }
    }

    public void inspect(final LocalDateTime inspectionTime) {
        if (this.status == ReplyStatus.REPLY) {
            throw new RainbowLetterException("already.send.reply.not.review", this.getId());
        }
        this.inspection = !this.inspection;
        this.inspectionTime = inspectionTime;
    }

    public void read() {
        if (this.status != ReplyStatus.REPLY) {
            throw new RainbowLetterException("need.send.reply", this.getId());
        }
        this.readStatus = ReplyReadStatus.READ;
    }

    public void submit(final LocalDateTime submitTime) {
        validateSubmit();
        this.status = ReplyStatus.REPLY;
        this.submitTime = submitTime;
        registerEvent(new SubmitReplyEvent(this));
    }

    private void validateSubmit() {
        if (!this.inspection) {
            throw new RainbowLetterException("need.review.reply", this.getId());
        }
        if (this.status == ReplyStatus.REPLY) {
            throw new RainbowLetterException("already.send.reply", this.getId());
        }
    }

    public enum ReplyStatus {
        CHAT_GPT, REPLY,
    }

    public enum ReplyReadStatus {
        READ, UNREAD,
    }

    public record ReplyId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
