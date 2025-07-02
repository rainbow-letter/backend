package com.rainbowletter.server.ai.adapter.out.persistence;

import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import static com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.ai.application.domain.model.Parameter;
import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ai_prompt")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class AiPromptJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long configId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AiProvider provider;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PromptType type;

    @NotNull
    private String model;

    @NotNull
    @Column(name = "`system`", columnDefinition = "TEXT")
    private String system;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String user;

    @Convert(converter = AiPromptParameterConverter.class)
    private List<Parameter> parameters = new ArrayList<>();

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final AiPromptJpaEntity aiPromptJpaEntity)) {
            return false;
        }
        return Objects.equals(id, aiPromptJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
