package com.rainbowletter.server.temporary.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.Objects;

@PersistenceMapper
class TemporaryMapper {

    Temporary mapToDomain(final TemporaryJpaEntity jpaEntity) {
        return Temporary.withId(
            new TemporaryId(jpaEntity.getId()),
            new UserId(jpaEntity.getUserId()),
            new PetId(jpaEntity.getPetId()),
            jpaEntity.getSessionId(),
            jpaEntity.getContent(),
            jpaEntity.getStatus(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }

    TemporaryJpaEntity mapToJpaEntity(final Temporary domain) {
        return new TemporaryJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getUserId().value(),
            domain.getPetId().value(),
            domain.getSessionId(),
            domain.getContent(),
            domain.getStatus()
        );
    }

}
