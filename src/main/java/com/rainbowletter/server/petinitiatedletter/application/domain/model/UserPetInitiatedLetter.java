package com.rainbowletter.server.petinitiatedletter.application.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
    name = "user_pet_initiated_letter",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "pet_id"})
    }
)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class UserPetInitiatedLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private Long petId;

    @NotNull
    private Long userId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
