package com.rainbowletter.server.reply.adapter.out.persistence;


import static com.rainbowletter.server.letter.adapter.out.persistence.QLetterJpaEntity.letterJpaEntity;
import static com.rainbowletter.server.reply.adapter.out.persistence.QReplyJpaEntity.replyJpaEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.reply.application.port.out.DeleteReplyPort;
import com.rainbowletter.server.reply.application.port.out.ExistsReplyPort;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ReplyPersistenceAdapter
    implements SaveReplyPort, ExistsReplyPort, LoadReplyPort, DeleteReplyPort {

    private final JPAQueryFactory queryFactory;
    private final ReplyMapper replyMapper;
    private final ReplyJpaRepository replyJpaRepository;

    @Override
    public Reply save(final Reply reply) {
        final var mappedToJpaEntity = replyMapper.mapToJpaEntity(reply);
        return replyMapper.mapToDomain(replyJpaRepository.save(mappedToJpaEntity));
    }

    @Override
    public void saveAll(final List<Reply> replies) {
        replyJpaRepository.saveAll(
            replies.stream()
                .map(replyMapper::mapToJpaEntity)
                .toList()
        );
    }

    @Override
    public void deleteByLetterId(final LetterId letterId) {
        replyJpaRepository.deleteByLetterId(letterId.value());
    }

    @Override
    public boolean existsByLetterId(final LetterId letterId) {
        return replyJpaRepository.existsByLetterId(letterId.value());
    }

    @Override
    public Reply loadReplyById(final ReplyId id) {
        final ReplyJpaEntity replyJpaEntity = replyJpaRepository.findById(id.value())
            .orElseThrow(() ->
                new RainbowLetterException("답장을 찾을 수 없습니다.", "id: [%d]".formatted(id.value())));
        return replyMapper.mapToDomain(replyJpaEntity);
    }

    @Override
    public Optional<Reply> loadReplyByLetterIdAndStatus(
        final LetterId letterId,
        final ReplyStatus status
    ) {
        return Optional.ofNullable(
                queryFactory.selectFrom(replyJpaEntity)
                    .join(letterJpaEntity).on(replyJpaEntity.letterId.eq(letterJpaEntity.id))
                    .where(
                        statusExpression(status),
                        letterJpaEntity.id.eq(letterId.value())
                    )
                    .fetchOne()
            )
            .map(replyMapper::mapToDomain);
    }

    private BooleanExpression statusExpression(final ReplyStatus status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return replyJpaEntity.status.eq(status);
    }

    @Override
    public Reply loadReplyByShareLink(final UUID shareLink) {
        return replyMapper.mapToDomain(
            Optional.ofNullable(
                    queryFactory.selectFrom(replyJpaEntity)
                        .join(letterJpaEntity).on(replyJpaEntity.letterId.eq(letterJpaEntity.id))
                        .where(
                            replyJpaEntity.status.eq(ReplyStatus.REPLY)
                                .and(letterJpaEntity.shareLink.eq(shareLink)))
                        .fetchOne()
                )
                .orElseThrow(() -> new RainbowLetterException("답장을 찾을 수 없습니다.",
                    "share: [%s]".formatted(shareLink)))
        );
    }

    @Override
    public List<Reply> loadRepliesByReservation() {
        return queryFactory.selectFrom(replyJpaEntity)
            .where(
                replyJpaEntity.inspection.isTrue()
                    .and(replyJpaEntity.status.eq(ReplyStatus.CHAT_GPT))
            )
            .fetch()
            .stream()
            .map(replyMapper::mapToDomain)
            .toList();
    }

}
