package com.rainbowletter.server.pet.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
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
            domain.getDeathAnniversary()
        );
    }

}
