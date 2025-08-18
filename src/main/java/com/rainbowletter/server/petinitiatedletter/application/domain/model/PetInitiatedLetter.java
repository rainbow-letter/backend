package com.rainbowletter.server.petinitiatedletter.application.domain.model;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.GeneratedLetterContent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "pet_initiated_letter")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PetInitiatedLetter extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long petId;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID shareLink;

    @Column(length = 20)
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String promptA;

    @Column(columnDefinition = "TEXT")
    private String promptB;

    @Enumerated(EnumType.STRING)
    private PromptType promptType;

    private LocalDateTime submitTime;

    @NotNull
    private boolean readStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetInitiatedLetterStatus status;

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

    public void generate(GeneratedLetterContent generatedLetterContent) {
        this.summary = generatedLetterContent.summary();
        this.content = generatedLetterContent.content();
        this.promptA = generatedLetterContent.promptA();
        this.promptB = generatedLetterContent.promptB();
        this.promptType = generatedLetterContent.selectedPrompt();
        this.status = PetInitiatedLetterStatus.READY_TO_SEND;
    }

    public void submit(final LocalDateTime submitTime) {
        validateSubmit();
        this.status = PetInitiatedLetterStatus.SENT;
        this.submitTime = submitTime;
    }

    private void validateSubmit() {
        if (this.status == PetInitiatedLetterStatus.SENT) {
            throw new RainbowLetterException("이미 발송된 편지입니다.", this.getId());
        }
    }
}