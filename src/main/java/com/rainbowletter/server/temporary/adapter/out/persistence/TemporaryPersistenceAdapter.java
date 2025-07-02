package com.rainbowletter.server.temporary.adapter.out.persistence;

import static com.rainbowletter.server.temporary.adapter.out.persistence.QTemporaryJpaEntity.temporaryJpaEntity;
import static com.rainbowletter.server.user.adapter.out.persistence.QUserJpaEntity.userJpaEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryStatus;
import com.rainbowletter.server.temporary.application.port.out.ExistsTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.LoadTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.SaveTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.UpdateTemporaryStatePort;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryPersistenceAdapter implements
    SaveTemporaryPort, ExistsTemporaryPort, LoadTemporaryPort, UpdateTemporaryStatePort {

    private static final String TEMPORARY_NOT_FOUND_MESSAGE = "편지를 찾을 수 없습니다.";

    private final JPAQueryFactory queryFactory;
    private final TemporaryMapper temporaryMapper;
    private final TemporaryJpaRepository temporaryJpaRepository;

    @Override
    public boolean existsEmailAndPetId(final String email, final PetId petId) {
        return Objects.nonNull(
            queryFactory.selectOne()
                .from(temporaryJpaEntity)
                .join(userJpaEntity).on(temporaryJpaEntity.userId.eq(userJpaEntity.id))
                .where(
                    userJpaEntity.email.eq(email),
                    temporaryJpaEntity.petId.eq(petId.value()),
                    temporaryJpaEntity.status.eq(TemporaryStatus.SAVE)
                )
                .fetchFirst()
        );
    }

    @Override
    public Temporary loadByEmailAndId(final String email, final TemporaryId id) {
        return temporaryMapper.mapToDomain(
            Optional.ofNullable(
                queryFactory.selectFrom(temporaryJpaEntity)
                    .join(userJpaEntity).on(temporaryJpaEntity.userId.eq(userJpaEntity.id))
                    .where(
                        userJpaEntity.email.eq(email),
                        temporaryJpaEntity.id.eq(id.value()),
                        temporaryJpaEntity.status.eq(TemporaryStatus.SAVE)
                    )
                    .fetchFirst()
            ).orElseThrow(() -> new RainbowLetterException(TEMPORARY_NOT_FOUND_MESSAGE))
        );
    }

    @Override
    public Temporary loadByEmailAndPetId(final String email, final PetId petId) {
        return temporaryMapper.mapToDomain(
            Optional.ofNullable(
                queryFactory.selectFrom(temporaryJpaEntity)
                    .join(userJpaEntity).on(temporaryJpaEntity.userId.eq(userJpaEntity.id))
                    .where(
                        userJpaEntity.email.eq(email),
                        temporaryJpaEntity.petId.eq(petId.value()),
                        temporaryJpaEntity.status.eq(TemporaryStatus.SAVE)
                    )
                    .fetchFirst()
            ).orElseThrow(() -> new RainbowLetterException(TEMPORARY_NOT_FOUND_MESSAGE))
        );
    }

    @Override
    public Temporary save(final Temporary temporary) {
        final var mappedToJpaEntity = temporaryMapper.mapToJpaEntity(temporary);
        return temporaryMapper.mapToDomain(temporaryJpaRepository.save(mappedToJpaEntity));
    }

    @Override
    public Temporary updateTemporary(final Temporary temporary) {
        return save(temporary);
    }

}
