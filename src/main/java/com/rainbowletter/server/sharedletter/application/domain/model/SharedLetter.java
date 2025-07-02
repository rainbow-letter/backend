package com.rainbowletter.server.sharedletter.application.domain.model;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "shared_letter")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class SharedLetter extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "recipient_type", nullable = false)
    private RecipientType recipientType;

    @NotNull
    private Long petId;

    @NotNull
    private Long userId;

    @Size(max = 56)
    @NotNull
    @Column(name = "content", nullable = false, length = 56)
    private String content;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof final SharedLetter sharedLetter)) return false;
        return Objects.equals(id, sharedLetter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
