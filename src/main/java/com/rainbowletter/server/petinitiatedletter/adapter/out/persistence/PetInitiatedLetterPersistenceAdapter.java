package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.pet.application.port.in.dto.PetForAdminResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.*;
import com.rainbowletter.server.user.application.port.in.dto.UserForAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.rainbowletter.server.pet.adapter.out.persistence.QPetJpaEntity.petJpaEntity;
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
                petInitiatedLetter.content,
                petInitiatedLetter.readStatus
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

    public PetInitiatedLetterForAdminResponse getPetInitiatedLetterDetailForAdmin(Long letterId, Long userId, Long petId) {
        PetInitiatedLetterForAdminResponse result = queryFactory.select(Projections.constructor(
                PetInitiatedLetterForAdminResponse.class,
                Projections.constructor(
                    UserForAdminResponse.class,
                    userJpaEntity.id,
                    userJpaEntity.email,
                    userJpaEntity.phoneNumber,
                    userJpaEntity.lastLoggedIn,
                    userJpaEntity.createdAt,
                    userJpaEntity.petInitiatedLetterEnabled
                ),
                Expressions.constant(0L),
                Projections.constructor(
                    PetForAdminResponse.class,
                    petJpaEntity.id,
                    petJpaEntity.name,
                    petJpaEntity.owner,
                    petJpaEntity.species,
                    petJpaEntity.personalities,
                    petJpaEntity.deathAnniversary
                ),
                Expressions.constant(Collections.emptyList()),
                Projections.constructor(
                    PetInitiatedLetterDetailResponse.class,
                    petInitiatedLetter.id,
                    petInitiatedLetter.status,
                    petInitiatedLetter.submitTime,
                    petInitiatedLetter.createdAt,
                    petInitiatedLetter.promptA,
                    petInitiatedLetter.promptB
                )
            ))
            .from(petInitiatedLetter)
            .join(userJpaEntity).on(petInitiatedLetter.userId.eq(userJpaEntity.id))
            .join(petJpaEntity).on(petInitiatedLetter.petId.eq(petJpaEntity.id))
            .where(
                petInitiatedLetter.id.eq(letterId),
                userJpaEntity.id.eq(userId),
                petJpaEntity.id.eq(petId)
            )
            .fetchOne();

        if (result == null) {
            throw new RainbowLetterException(
                "해당 선편지 정보를 찾을 수 없습니다.",
                "UserId : " + userId + " PetId : " + petId + " PetInitiatedLetterId : " + letterId
            );
        }

        return result;
    }

    public List<PetInitiatedLettersForAdminResponse> getPetInitiatedLetterListByUserId(Long userId) {
        return queryFactory.select(Projections.constructor(
                PetInitiatedLettersForAdminResponse.class,
                petInitiatedLetter.id,
                petJpaEntity.name,
                petInitiatedLetter.summary,
                petInitiatedLetter.status,
                petInitiatedLetter.createdAt
            ))
            .from(petInitiatedLetter)
            .join(petJpaEntity).on(petInitiatedLetter.petId.eq(petJpaEntity.id))
            .where(
                petInitiatedLetter.userId.eq(userId),
                petJpaEntity.userId.eq(userId)
            )
            .orderBy(petInitiatedLetter.createdAt.desc())
            .fetch();
    }

    public PetInitiatedLetterWithPetNameResponse getLetterByShareLink(UUID shareLink) {
        return queryFactory.select(Projections.constructor(
                PetInitiatedLetterWithPetNameResponse.class,
                petInitiatedLetter.id,
                petInitiatedLetter.createdAt,
                petInitiatedLetter.submitTime,
                petInitiatedLetter.summary,
                petInitiatedLetter.content,
                petJpaEntity.id,
                petJpaEntity.name,
                petJpaEntity.image
            ))
            .from(petInitiatedLetter)
            .join(petJpaEntity).on(petInitiatedLetter.petId.eq(petJpaEntity.id))
            .where(
                petInitiatedLetter.shareLink.eq(shareLink)
            )
            .fetchOne();
    }

    public PetInitiatedLetterSummary getPetInitiatedLetterDetail(Long userId, Long letterId) {
        PetInitiatedLetterSummary result = queryFactory.select(Projections.constructor(
                PetInitiatedLetterSummary.class,
                petInitiatedLetter.id,
                petInitiatedLetter.createdAt,
                petInitiatedLetter.content,
                petJpaEntity.id,
                petJpaEntity.name,
                petJpaEntity.image
            ))
            .from(petInitiatedLetter)
            .join(petJpaEntity).on(petInitiatedLetter.petId.eq(petJpaEntity.id))
            .where(
                petInitiatedLetter.id.eq(letterId),
                petInitiatedLetter.userId.eq(userId)
            )
            .fetchOne();

        if (result == null) {
            throw new RainbowLetterException("자신의 선편지만 조회할 수 있습니다.");
        }

        return result;
    }
}
