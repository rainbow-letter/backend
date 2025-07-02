package com.rainbowletter.server.letter.adapter.out.persistence;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "letter")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class LetterJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID shareLink;

    private String image;

    @NotNull
    private Integer number;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LetterStatus status;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final LetterJpaEntity letterJpaEntity)) {
            return false;
        }
        return Objects.equals(id, letterJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
