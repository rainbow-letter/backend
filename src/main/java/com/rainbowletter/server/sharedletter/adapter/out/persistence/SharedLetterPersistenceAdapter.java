package com.rainbowletter.server.sharedletter.adapter.out.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.pet.application.port.in.dto.PetSimpleSummary;
import com.rainbowletter.server.sharedletter.adapter.in.web.dto.SharedLetterResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.rainbowletter.server.pet.adapter.out.persistence.QPetJpaEntity.petJpaEntity;
import static com.rainbowletter.server.sharedletter.application.domain.model.QSharedLetter.sharedLetter;

@PersistenceAdapter
@RequiredArgsConstructor
public class SharedLetterPersistenceAdapter {

    private final JPAQueryFactory queryFactory;

    public List<SharedLetterResponse> retrieveByUser(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
            .select(Projections.constructor(
                SharedLetterResponse.class,
                sharedLetter.id,
                sharedLetter.content,
                sharedLetter.recipientType,
                Projections.constructor(
                    PetSimpleSummary.class,
                    petJpaEntity.id,
                    petJpaEntity.name,
                    petJpaEntity.image
                )
            ))
            .from(sharedLetter)
            .leftJoin(petJpaEntity).on(sharedLetter.petId.eq(petJpaEntity.id))
            .where(
                sharedLetter.userId.eq(userId),
                startDate != null ? sharedLetter.createdAt.goe(startDate) : null,
                endDate != null ? sharedLetter.createdAt.loe(endDate) : null
            )
            .orderBy(sharedLetter.id.asc())
            .fetch();
    }

    public List<SharedLetterResponse> retrieve(Long userId, Long after, LocalDateTime startDate, LocalDateTime endDate, int limit, boolean isRandomSort) {
        return queryFactory
            .select(Projections.constructor(
                SharedLetterResponse.class,
                sharedLetter.id,
                sharedLetter.content,
                sharedLetter.recipientType,
                Projections.constructor(
                    PetSimpleSummary.class,
                    petJpaEntity.id,
                    petJpaEntity.name,
                    petJpaEntity.image
                )
            ))
            .from(sharedLetter)
            .leftJoin(petJpaEntity).on(sharedLetter.petId.eq(petJpaEntity.id))
            .where(
                sharedLetter.deletedAt.isNull(),
                sharedLetter.userId.ne(userId),
                after != null ? sharedLetter.id.gt(after) : null,
                startDate != null ? sharedLetter.createdAt.gt(startDate) : null,
                endDate != null ? sharedLetter.createdAt.lt(endDate) : null
            )
            .orderBy(
                isRandomSort ? Expressions.numberTemplate(Double.class, "rand()").asc() : sharedLetter.id.asc()
            )
            .limit(limit)
            .fetch();
    }

}