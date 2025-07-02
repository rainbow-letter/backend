package com.rainbowletter.server.ai.adapter.out.persistence;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ai_config")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class AiConfigJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean useABTest;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PromptType selectPrompt;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final AiConfigJpaEntity aiConfigJpaEntity)) {
            return false;
        }
        return Objects.equals(id, aiConfigJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
