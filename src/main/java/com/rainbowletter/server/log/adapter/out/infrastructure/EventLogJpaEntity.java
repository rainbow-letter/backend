package com.rainbowletter.server.log.adapter.out.infrastructure;

import static com.rainbowletter.server.log.application.domain.model.EventLog.EventLogStatus;
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
@Table(name = "log_event")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class EventLogJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long resource;

    private Long userId;

    @NotNull
    private String category;

    @NotNull
    private String event;

    @NotNull
    private String message;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private EventLogStatus status;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final EventLogJpaEntity eventLogJpaEntity)) {
            return false;
        }
        return Objects.equals(id, eventLogJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
