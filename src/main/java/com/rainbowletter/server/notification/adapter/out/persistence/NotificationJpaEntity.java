package com.rainbowletter.server.notification.adapter.out.persistence;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import com.rainbowletter.server.notification.application.domain.model.Notification.NotificationType;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class NotificationJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column(length = 50)
    private String receiver;

    @NotNull
    @Column(length = 50)
    private String sender;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @NotNull
    private int code;

    @NotNull
    private String statusMessage;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final NotificationJpaEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
