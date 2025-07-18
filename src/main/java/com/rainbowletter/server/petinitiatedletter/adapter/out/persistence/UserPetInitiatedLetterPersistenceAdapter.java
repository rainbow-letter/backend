package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.user.adapter.in.web.dto.PetSelectionResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.rainbowletter.server.pet.adapter.out.persistence.QPetJpaEntity.petJpaEntity;
import static com.rainbowletter.server.petinitiatedletter.application.domain.model.QUserPetInitiatedLetter.userPetInitiatedLetter;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPetInitiatedLetterPersistenceAdapter {

    private final JPAQueryFactory queryFactory;

    public List<PetSelectionResponse> findByUserId(Long userId) {
        return queryFactory.select(Projections.constructor(
                PetSelectionResponse.class,
                userPetInitiatedLetter.petId,
                petJpaEntity.name
            ))
            .from(userPetInitiatedLetter)
            .leftJoin(petJpaEntity).on(userPetInitiatedLetter.petId.eq(petJpaEntity.id))
            .where(
                userPetInitiatedLetter.userId.eq(userId)
            )
            .orderBy(userPetInitiatedLetter.createdAt.desc())
            .fetch();
    }

}
