package com.rainbowletter.server.pet.adapter.out.persistence;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import com.rainbowletter.server.common.adapter.out.persistence.JpaStringToListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "pet")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
class PetJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private String species;

    @NotNull
    private String owner;

    private String image;

    @Convert(converter = JpaStringToListConverter.class)
    private List<String> personalities = new ArrayList<>();

    private LocalDate deathAnniversary;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final PetJpaEntity petJpaEntity)) {
            return false;
        }
        return Objects.equals(id, petJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
