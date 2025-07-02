package com.rainbowletter.server.faq.application.domain.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Faq {

    private final FaqId id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private String summary;
    private String detail;
    private boolean visibility;
    private Long sequence;

    public static Faq withId(
        final FaqId id,
        final String summary,
        final String detail,
        final boolean visibility,
        final Long sequence,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        return new Faq(id, createdAt, updatedAt, summary, detail, visibility, sequence);
    }

    public static Faq withoutId(
        final String summary,
        final String detail,
        final boolean visibility
    ) {
        return new Faq(null, null, null, summary, detail, visibility, null);
    }

    public void updateContent(final String summary, final String detail) {
        this.summary = summary;
        this.detail = detail;
    }

    public void changeVisibility() {
        this.visibility = !visibility;
    }

    public void changeSequence(final Long sequence) {
        this.sequence = sequence;
    }

    public record FaqId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }

}
