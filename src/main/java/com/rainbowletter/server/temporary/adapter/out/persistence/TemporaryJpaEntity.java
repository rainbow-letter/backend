package com.rainbowletter.server.temporary.adapter.out.persistence;

import static com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryStatus;
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
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "temporary")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class TemporaryJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long petId;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sessionId;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TemporaryStatus status;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final TemporaryJpaEntity temporaryJpaEntity)) {
            return false;
        }
        return Objects.equals(id, temporaryJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
