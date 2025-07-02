package com.rainbowletter.server.pet.adapter.out.persistence;

import static lombok.AccessLevel.PROTECTED;

import com.rainbowletter.server.common.adapter.out.persistence.BaseTimeJpaEntity;
import com.rainbowletter.server.common.adapter.out.persistence.JpaStringToListConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "favorite_id", referencedColumnName = "id", nullable = false)
    private FavoriteJpaEntity favoriteJpaEntity;

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
