package com.rainbowletter.server.pet.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.pet.application.domain.model.Favorite;
import com.rainbowletter.server.pet.application.domain.model.Favorite.FavoriteId;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.Objects;

@PersistenceMapper
class PetMapper {

    Pet mapToPet(final PetJpaEntity jpaEntity) {
        return Pet.withId(
            new PetId(jpaEntity.getId()),
            new UserId(jpaEntity.getUserId()),
            mapToFavorite(jpaEntity.getFavoriteJpaEntity()),
            jpaEntity.getName(),
            jpaEntity.getSpecies(),
            jpaEntity.getOwner(),
            jpaEntity.getImage(),
            jpaEntity.getPersonalities(),
            jpaEntity.getDeathAnniversary(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }

    PetJpaEntity mapToPetEntity(final Pet domain) {
        return new PetJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getUserId().value(),
            domain.getName(),
            domain.getSpecies(),
            domain.getOwner(),
            domain.getImage(),
            domain.getPersonalities(),
            domain.getDeathAnniversary(),
            mapToFavoriteEntity(domain.getFavorite())
        );
    }

    Favorite mapToFavorite(final FavoriteJpaEntity jpaEntity) {
        return Favorite.withId(
            new FavoriteId(jpaEntity.getId()),
            jpaEntity.getTotal(),
            jpaEntity.getDayIncreaseCount(),
            jpaEntity.isCanIncrease(),
            jpaEntity.getLastIncreasedAt()
        );
    }

    FavoriteJpaEntity mapToFavoriteEntity(final Favorite domain) {
        return new FavoriteJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getTotal(),
            domain.getDayIncreaseCount(),
            domain.isCanIncrease(),
            domain.getLastIncreasedAt()
        );
    }

}
