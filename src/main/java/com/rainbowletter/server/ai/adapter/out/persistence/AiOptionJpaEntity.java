package com.rainbowletter.server.ai.adapter.out.persistence;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import com.rainbowletter.server.common.adapter.out.persistence.JpaStringToListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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
@Table(name = "ai_prompt_option")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class AiOptionJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long promptId;

    @NotNull
    private Integer maxTokens;

    @NotNull
    private Double temperature;

    @NotNull
    private Double topP;

    @NotNull
    private Double frequencyPenalty;

    @NotNull
    private Double presencePenalty;

    @Convert(converter = JpaStringToListConverter.class)
    private List<String> stop = new ArrayList<>();

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final AiOptionJpaEntity aiOptionJpaEntity)) {
            return false;
        }
        return Objects.equals(id, aiOptionJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
