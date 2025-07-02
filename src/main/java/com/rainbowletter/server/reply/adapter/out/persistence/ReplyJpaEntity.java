package com.rainbowletter.server.reply.adapter.out.persistence;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import static com.rainbowletter.server.reply.application.domain.model.Reply.ReplyReadStatus;
import static com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reply")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class ReplyJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long petId;

    @NotNull
    private Long letterId;

    @NotNull
    @Column(length = 20)
    private String summary;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String promptA;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String promptB;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PromptType promptType;

    @NotNull
    private boolean inspection;

    private LocalDateTime inspectionTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReplyStatus status;

    private LocalDateTime submitTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReplyReadStatus readStatus;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final ReplyJpaEntity replyJpaEntity)) {
            return false;
        }
        return Objects.equals(id, replyJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
