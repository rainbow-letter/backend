package com.rainbowletter.server.petinitiatedletter.application.domain.model;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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

}