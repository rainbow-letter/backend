package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterSimpleResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.RetrievePetInitiatedLettersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;

import static com.rainbowletter.server.petinitiatedletter.application.domain.model.QPetInitiatedLetter.petInitiatedLetter;
import static com.rainbowletter.server.user.adapter.out.persistence.QUserJpaEntity.userJpaEntity;


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

    public Page<PetInitiatedLetterResponse> getPetInitiatedLetters(
        RetrievePetInitiatedLettersRequest query,
        Pageable pageable
    ) {
        BooleanBuilder whereClause = buildWhereClause(query);

        List<PetInitiatedLetterResponse> letterList = queryFactory.select(Projections.constructor(
                PetInitiatedLetterResponse.class,
                petInitiatedLetter.id,
                petInitiatedLetter.createdAt,
                petInitiatedLetter.summary,
                petInitiatedLetter.content,
                petInitiatedLetter.status,
                userJpaEntity.email,
                userJpaEntity.id,
                userJpaEntity.petInitiatedLetterEnabled,
                petInitiatedLetter.submitTime
            ))
            .from(petInitiatedLetter)
            .join(userJpaEntity).on(userJpaEntity.id.eq(petInitiatedLetter.userId))
            .where(whereClause)
            .orderBy(petInitiatedLetter.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(petInitiatedLetter.count())
            .from(petInitiatedLetter)
            .join(userJpaEntity).on(userJpaEntity.id.eq(petInitiatedLetter.userId))
            .where(whereClause)
            .fetchOne();

        return new PageImpl<>(letterList, pageable, total != null ? total : 0L);
    }

    private BooleanBuilder buildWhereClause(RetrievePetInitiatedLettersRequest query) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(petInitiatedLetter.createdAt.between(
            query.searchDate().atStartOfDay(),
            query.searchDate().atTime(LocalTime.MAX)
        ));

        if (query.status() != null) {
            builder.and(petInitiatedLetter.status.eq(query.status()));
        }

        if (query.email() != null && !query.email().isBlank()) {
            builder.and(userJpaEntity.email.containsIgnoreCase(query.email()));
        }

        return builder;
    }

}
