package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterSimpleResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.rainbowletter.server.petinitiatedletter.application.domain.model.QPetInitiatedLetter.petInitiatedLetter;

@PersistenceAdapter
@RequiredArgsConstructor
public class PetInitiatedLetterPersistenceAdapter {

    private final JPAQueryFactory queryFactory;

    public List<PetInitiatedLetterSimpleResponse> findByPetId(Long petId, RetrieveLetterRequest query) {
        return queryFactory.select(Projections.constructor(
                PetInitiatedLetterSimpleResponse.class,
                petInitiatedLetter.id,
                petInitiatedLetter.createdAt,
                petInitiatedLetter.summary,
                petInitiatedLetter.content
            ))
            .from(petInitiatedLetter)
            .where(
                petInitiatedLetter.petId.eq(petId),
                query.after() != null ? petInitiatedLetter.id.lt(query.after()) : null,
                query.startDate() != null ? petInitiatedLetter.createdAt.goe(query.startDate()) : null,
                query.endDate() != null ? petInitiatedLetter.createdAt.loe(query.endDate()) : null
            )
            .orderBy(petInitiatedLetter.id.desc())
            .limit(query.limit() + 1L)
            .fetch();
    }

}
