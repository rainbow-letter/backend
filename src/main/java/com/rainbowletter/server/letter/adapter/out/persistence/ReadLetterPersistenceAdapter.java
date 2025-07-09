package com.rainbowletter.server.letter.adapter.out.persistence;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.letter.adapter.out.dto.RecentLetterSummary;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleResponse;
import com.rainbowletter.server.letter.application.port.out.CountLetterPort;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.letter.application.port.out.dto.LetterBoxResponse;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.slack.adapter.out.dto.LetterStats;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.querydsl.core.types.dsl.Expressions.asNumber;
import static com.rainbowletter.server.letter.adapter.out.persistence.QLetterJpaEntity.letterJpaEntity;
import static com.rainbowletter.server.pet.adapter.out.persistence.QPetJpaEntity.petJpaEntity;
import static com.rainbowletter.server.reply.adapter.out.persistence.QReplyJpaEntity.replyJpaEntity;
import static com.rainbowletter.server.user.adapter.out.persistence.QUserJpaEntity.userJpaEntity;

@PersistenceAdapter
@RequiredArgsConstructor
class ReadLetterPersistenceAdapter implements LoadLetterPort, CountLetterPort {

    private final JPAQueryFactory queryFactory;
    private final LetterMapper letterMapper;
    private final LetterJpaRepository letterJpaRepository;

    @Override
    public Letter loadLetterById(final LetterId id) {
        return letterMapper.mapToDomain(
            letterJpaRepository.findById(id.value())
                .orElseThrow(() ->
                    new RainbowLetterException("편지를 찾을 수 없습니다.", "id: [%d]".formatted(id.value())))
        );
    }

    @Override
    public Letter loadLetterByIdAndUserId(final LetterId id, final UserId userId) {
        return letterMapper.mapToDomain(
            letterJpaRepository.findByIdAndUserId(id.value(), userId.value())
                .orElseThrow(() -> new RainbowLetterException(
                    "사용자 ID로 편지를 찾을 수 없습니다.",
                    "id: [%d] userId: [%d]".formatted(id.value(), userId.value())
                ))
        );
    }

    @Override
    public Letter loadLetterByEmailAndId(final String email, final LetterId id) {
        return letterMapper.mapToDomain(
            Optional.ofNullable(
                    queryFactory.selectFrom(letterJpaEntity)
                        .join(userJpaEntity).on(letterJpaEntity.userId.eq(userJpaEntity.id))
                        .where(userJpaEntity.email.eq(email).and(letterJpaEntity.id.eq(id.value())))
                        .fetchOne()
                )
                .orElseThrow(() -> new RainbowLetterException(
                    "사용자 이메일로 편지를 찾을 수 없습니다.",
                    "email: [%s] id: [%d]".formatted(email, id.value())
                ))
        );
    }

    @Override
    public Letter loadLetterByShareLink(final UUID shareLink) {
        return letterMapper.mapToDomain(
            Optional.ofNullable(
                    queryFactory.selectFrom(letterJpaEntity)
                        .where(letterJpaEntity.shareLink.eq(shareLink))
                        .fetchFirst()
                )
                .orElseThrow(() -> new RainbowLetterException(
                    "공유 링크로 편지를 찾을 수 없습니다.",
                    "share: [%s]".formatted(shareLink)
                ))
        );
    }

    @Override
    public List<Letter> loadLettersByPetId(final PetId petId) {
        return letterJpaRepository.findAllByPetId(petId.value())
            .stream()
            .map(letterMapper::mapToDomain)
            .toList();
    }

    @Override
    public List<LetterBoxResponse> loadLetterBox(
        final String email,
        final PetId petId,
        final LocalDateTime startDateTime,
        final LocalDateTime endDateTime
    ) {
        return queryFactory.select(Projections.constructor(
                LetterBoxResponse.class,
                letterJpaEntity.id,
                letterJpaEntity.number,
                letterJpaEntity.summary,
                letterJpaEntity.status,
                petJpaEntity.name,
                replyJpaEntity.readStatus,
                letterJpaEntity.createdAt
            ))
            .from(letterJpaEntity)
            .join(userJpaEntity).on(letterJpaEntity.userId.eq(userJpaEntity.id))
            .join(petJpaEntity).on(letterJpaEntity.petId.eq(petJpaEntity.id))
            .leftJoin(replyJpaEntity).on(letterJpaEntity.id.eq(replyJpaEntity.letterId))
            .where(
                emailExpression(email),
                petExpression(petId.value()),
                dateExpression(startDateTime, endDateTime)
            )
            .orderBy(letterJpaEntity.createdAt.desc())
            .fetch();
    }

    @Override
    public List<LetterAdminRecentResponse> loadRecentLettersByUserId(final UserId userId) {
        return queryFactory.select(Projections.constructor(
                LetterAdminRecentResponse.class,
                letterJpaEntity.id,
                letterJpaEntity.userId,
                letterJpaEntity.petId,
                letterJpaEntity.number,
                petJpaEntity.name,
                letterJpaEntity.summary,
                letterJpaEntity.content,
                replyJpaEntity.inspection,
                replyJpaEntity.status,
                letterJpaEntity.createdAt,
                letterJpaEntity.updatedAt
            ))
            .distinct()
            .from(letterJpaEntity)
            .join(petJpaEntity).on(letterJpaEntity.petId.eq(petJpaEntity.id))
            .leftJoin(replyJpaEntity).on(letterJpaEntity.id.eq(replyJpaEntity.letterId))
            .where(letterJpaEntity.userId.eq(userId.value()))
            .limit(20)
            .orderBy(letterJpaEntity.createdAt.desc())
            .fetch();
    }

    @Override
    public Page<LetterAdminPageResponse> loadLettersFromAdmin(
        final LocalDateTime startDateTime,
        final LocalDateTime endDateTime,
        final ReplyStatus status,
        final String email,
        final Boolean inspect,
        final Pageable pageable
    ) {
        final JPQLQuery<Long> letterCountQuery = JPAExpressions.select(letterJpaEntity.count())
            .from(letterJpaEntity)
            .where(letterJpaEntity.userId.eq(userJpaEntity.id));
        final NumberExpression<Long> letterCount = asNumber(
            ExpressionUtils.as(letterCountQuery, Expressions.numberPath(Long.class, "count")));

        final List<LetterAdminPageResponse> result = queryFactory.select(Projections.constructor(
                LetterAdminPageResponse.class,
                letterJpaEntity.id,
                letterJpaEntity.userId,
                letterJpaEntity.petId,
                replyJpaEntity.id,
                userJpaEntity.email,
                letterCount,
                letterJpaEntity.summary,
                letterJpaEntity.content,
                replyJpaEntity.promptType,
                replyJpaEntity.inspection,
                replyJpaEntity.inspectionTime,
                replyJpaEntity.status,
                replyJpaEntity.submitTime,
                letterJpaEntity.createdAt,
                letterJpaEntity.updatedAt
            ))
            .distinct()
            .from(letterJpaEntity)
            .join(userJpaEntity).on(letterJpaEntity.userId.eq(userJpaEntity.id))
            .leftJoin(replyJpaEntity).on(letterJpaEntity.id.eq(replyJpaEntity.letterId))
            .where(
                emailExpression(email),
                inspectExpression(inspect),
                statusExpression(status),
                dateExpression(startDateTime, endDateTime)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(letterJpaEntity.createdAt.desc())
            .fetch();

        final JPAQuery<Long> totalLetterPageQuery = queryFactory
            .select(letterJpaEntity.count())
            .from(letterJpaEntity)
            .join(userJpaEntity).on(letterJpaEntity.userId.eq(userJpaEntity.id))
            .leftJoin(replyJpaEntity).on(letterJpaEntity.id.eq(replyJpaEntity.letterId))
            .where(
                emailExpression(email),
                inspectExpression(inspect),
                statusExpression(status),
                dateExpression(startDateTime, endDateTime)
            );
        return PageableExecutionUtils
            .getPage(result, pageable, totalLetterPageQuery::fetchOne);
    }

    private BooleanExpression emailExpression(final String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        return userJpaEntity.email.containsIgnoreCase(email);
    }

    private BooleanExpression petExpression(final Long petId) {
        if (Objects.isNull(petId)) {
            return null;
        }
        return letterJpaEntity.petId.eq(petId);
    }

    private BooleanExpression inspectExpression(final Boolean inspect) {
        if (Objects.isNull(inspect)) {
            return null;
        }
        return replyJpaEntity.inspection.eq(inspect);
    }

    private BooleanExpression statusExpression(final ReplyStatus status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return replyJpaEntity.status.eq(status);
    }

    private BooleanExpression dateExpression(
        final LocalDateTime startDateTime,
        final LocalDateTime endDateTime
    ) {
        if (Objects.isNull(startDateTime) || Objects.isNull(endDateTime)) {
            return null;
        }
        final BooleanExpression isGoeStartDate = letterJpaEntity.createdAt.goe(startDateTime);
        final BooleanExpression isLoeEndDate = letterJpaEntity.createdAt.loe(endDateTime);
        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }

    @Override
    public Long countByUserId(final UserId userId) {
        return queryFactory.select(letterJpaEntity.count())
            .from(letterJpaEntity)
            .where(letterJpaEntity.userId.eq(userId.value()))
            .fetchFirst();
    }

    @Override
    public Long countByPetId(final PetId petId) {
        return queryFactory.select(letterJpaEntity.count())
            .from(letterJpaEntity)
            .where(letterJpaEntity.petId.eq(petId.value()))
            .fetchFirst();
    }

    @Override
    public Integer getLastNumber(final String email, final PetId petId) {
        final Optional<LetterJpaEntity> result = Optional.ofNullable(
            queryFactory.selectFrom(letterJpaEntity)
                .join(userJpaEntity).on(letterJpaEntity.userId.eq(userJpaEntity.id))
                .where(
                    emailExpression(email),
                    petExpression(petId.value())
                )
                .orderBy(letterJpaEntity.createdAt.desc())
                .fetchFirst()
        );
        return result.isPresent() ? result.get().getNumber() : 0;
    }

    @Override
    public Long countByPetIdAndEndDate(Long petId, LocalDateTime endDate) {
        return queryFactory.select(letterJpaEntity.count())
            .from(letterJpaEntity)
            .where(
                letterJpaEntity.petId.eq(petId),
                endDate != null ? letterJpaEntity.createdAt.loe(endDate) : null
            )
            .fetchOne();
    }

//    @Override
//    public List<LetterWithReply> loadRecentLettersAndRepliesByPetId(final PetId petId) {
//        return queryFactory
//            .select(Projections.constructor(
//                LetterWithReply.class,
//                letterJpaEntity.id,
//                letterJpaEntity.content,
//                replyJpaEntity.content
//            ))
//            .from(letterJpaEntity)
//            .leftJoin(replyJpaEntity).on(letterJpaEntity.id.eq(replyJpaEntity.letterId))
//            .where(letterJpaEntity.petId.eq(petId.value()))
//            .orderBy(letterJpaEntity.createdAt.desc())
//            .limit(3)
//            .fetch();
//    }

    @Override
    public List<RecentLetterSummary> loadRecentLettersByPetId(final PetId petId,
                                                              final Long currentLetterId,
                                                              final LocalDateTime currentCreatedAt
    ) {
        final LocalDateTime startDate = currentCreatedAt.toLocalDate().minusDays(5).atStartOfDay();

        return queryFactory
            .select(Projections.constructor(
                RecentLetterSummary.class,
                letterJpaEntity.id,
                letterJpaEntity.content
            ))
            .from(letterJpaEntity)
            .where(
                letterJpaEntity.petId.eq(petId.value()),
                letterJpaEntity.id.ne(currentLetterId),
                letterJpaEntity.createdAt.goe(startDate)
            )
            .orderBy(letterJpaEntity.createdAt.desc())
            .limit(3)
            .fetch();
    }

    @Override
    public LetterStats getLetterReportByCreatedAtBetween(LocalDateTime letterStartTime, LocalDateTime letterEndTime) {
        LetterStats result = queryFactory
            .select(
                Projections.constructor(
                    LetterStats.class,
                    letterJpaEntity.id.countDistinct(),
                    countByCondition(replyJpaEntity.inspection.eq(false)),
                    countByCondition(replyJpaEntity.status.eq(ReplyStatus.REPLY)),
                    countByCondition(replyJpaEntity.status.eq(ReplyStatus.CHAT_GPT))
                )
            )
            .from(letterJpaEntity)
            .leftJoin(replyJpaEntity).on(letterJpaEntity.id.eq(replyJpaEntity.letterId))
            .where(
                letterJpaEntity.createdAt.goe(letterStartTime),
                letterJpaEntity.createdAt.lt(letterEndTime)
            )
            .fetchOne();

        return result != null ? result : new LetterStats(0L, 0L, 0L, 0L);
    }

    @Override
    public List<LetterSimpleResponse> findByPetId(Long petId, RetrieveLetterRequest query) {
        return queryFactory.select(Projections.constructor(
                LetterSimpleResponse.class,
                letterJpaEntity.id,
                letterJpaEntity.createdAt,
                letterJpaEntity.summary,
                letterJpaEntity.content,
                letterJpaEntity.status,
                letterJpaEntity.image
            ))
            .from(letterJpaEntity)
            .where(
                letterJpaEntity.petId.eq(petId),
                query.after() != null ? letterJpaEntity.id.lt(query.after()) : null,
                query.startDate() != null ? letterJpaEntity.createdAt.goe(query.startDate()) : null,
                query.endDate() != null ? letterJpaEntity.createdAt.loe(query.endDate()) : null
            )
            .orderBy(letterJpaEntity.id.desc())
            .limit(query.limit() + 1L)
            .fetch();
    }

    private NumberExpression<Long> countByCondition(BooleanExpression condition) {
        return Expressions.cases()
            .when(condition).then(1L)
            .otherwise(0L)
            .sum()
            .coalesce(0L);
    }

}
