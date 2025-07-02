package com.rainbowletter.server.letter.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.Objects;

@PersistenceMapper
class LetterMapper {

    Letter mapToDomain(final LetterJpaEntity jpaEntity) {
        return Letter.withId(
            new LetterId(jpaEntity.getId()),
            new UserId(jpaEntity.getUserId()),
            new PetId(jpaEntity.getPetId()),
            jpaEntity.getSummary(),
            jpaEntity.getContent(),
            jpaEntity.getShareLink(),
            jpaEntity.getImage(),
            jpaEntity.getNumber(),
            jpaEntity.getStatus(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }

    LetterJpaEntity mapToJpaEntity(final Letter domain) {
        return new LetterJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getUserId().value(),
            domain.getPetId().value(),
            domain.getSummary(),
            domain.getContent(),
            domain.getShareLink(),
            domain.getImage(),
            domain.getNumber(),
            domain.getStatus()
        );
    }

}
