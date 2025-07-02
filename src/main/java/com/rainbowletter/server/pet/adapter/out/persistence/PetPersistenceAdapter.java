package com.rainbowletter.server.pet.adapter.out.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;
import com.rainbowletter.server.pet.application.port.out.*;
import com.rainbowletter.server.pet.application.port.out.dto.PetDashboardResponse;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rainbowletter.server.letter.adapter.out.persistence.QLetterJpaEntity.letterJpaEntity;
import static com.rainbowletter.server.pet.adapter.out.persistence.QPetJpaEntity.petJpaEntity;
import static com.rainbowletter.server.user.adapter.out.persistence.QUserJpaEntity.userJpaEntity;

@PersistenceAdapter
@RequiredArgsConstructor
class PetPersistenceAdapter implements CreatePetPort, LoadPetPort, LoadPetDashboardPort,
        ResetFavoriteStatePort, UpdatePetStatePort, DeletePetPort {

    private final JPAQueryFactory queryFactory;
    private final PetMapper petMapper;
    private final PetJpaRepository petJpaRepository;

    private Pet save(final Pet pet) {
        final var mappedToJpaEntity = petMapper.mapToPetEntity(pet);
        return petMapper.mapToPet(petJpaRepository.save(mappedToJpaEntity));
    }

    @Override
    public Pet createPet(final Pet pet) {
        return save(pet);
    }

    @Override
    public void resetFavorite() {
        queryFactory.update(petJpaEntity)
                .set(petJpaEntity.favoriteJpaEntity.canIncrease, true)
                .set(petJpaEntity.favoriteJpaEntity.dayIncreaseCount, 0)
                .execute();
    }

    @Override
    public Pet loadPetByIdAndUserId(final PetId petId, final UserId userId) {
        return petJpaRepository.findByIdAndUserId(petId.value(), userId.value())
                .map(petMapper::mapToPet)
                .orElseThrow(() -> new RainbowLetterException(
                                "반려동물 정보를 찾을 수 없습니다.",
                                "petId: [%d] userId: [%d]".formatted(petId.value(), userId.value())
                        )
                );
    }

    @Override
    public Pet loadPetByLetterId(final LetterId letterId) {
        return petMapper.mapToPet(
                Optional.ofNullable(
                                queryFactory.selectFrom(petJpaEntity)
                                        .join(letterJpaEntity).on(petJpaEntity.id.eq(letterJpaEntity.petId))
                                        .where(letterJpaEntity.id.eq(letterId.value()))
                                        .fetchOne()
                        )
                        .orElseThrow(() -> new RainbowLetterException("반려동물을 찾을 수 없습니다.",
                                "letter: [%d]".formatted(letterId.value())))
        );
    }

    @Override
    public Pet loadPetByShareLink(final UUID shareLink) {
        return petMapper.mapToPet(
                Optional.ofNullable(
                                queryFactory.selectFrom(petJpaEntity)
                                        .join(letterJpaEntity).on(petJpaEntity.id.eq(letterJpaEntity.petId))
                                        .where(letterJpaEntity.shareLink.eq(shareLink))
                                        .fetchOne()
                        )
                        .orElseThrow(() -> new RainbowLetterException("반려동물을 찾을 수 없습니다.",
                                "share: [%s]".formatted(shareLink)))
        );
    }

    @Override
    public List<Pet> loadPetsByUserId(final UserId userId) {
        return petJpaRepository.findAllByUserId(userId.value())
                .stream()
                .map(petMapper::mapToPet)
                .toList();
    }

    @Override
    public PetSummary findPetSummaryById(Long petId, Long userId) {
        return queryFactory.select(Projections.constructor(
                PetSummary.class,
                petJpaEntity.id,
                petJpaEntity.name,
                petJpaEntity.deathAnniversary,
                petJpaEntity.image,
                petJpaEntity.personalities,
                petJpaEntity.owner
            ))
            .from(petJpaEntity)
            .where(
                    petJpaEntity.id.eq(petId),
                    petJpaEntity.userId.eq(userId)
            )
            .fetchOne();
    }

    @Override
    public List<PetDashboardResponse> loadPetDashboard(final String email) {
        return queryFactory.select(Projections.constructor(
                        PetDashboardResponse.class,
                        petJpaEntity.id,
                        petJpaEntity.name,
                        letterJpaEntity.count().as("letterCount"),
                        petJpaEntity.favoriteJpaEntity.total.as("favoriteCount"),
                        petJpaEntity.image,
                        petJpaEntity.deathAnniversary
                ))
                .from(petJpaEntity)
                .join(userJpaEntity).on(petJpaEntity.userId.eq(userJpaEntity.id))
                .leftJoin(letterJpaEntity).on(petJpaEntity.id.eq(letterJpaEntity.petId))
                .where(userJpaEntity.email.eq(email))
                .groupBy(petJpaEntity.id)
                .orderBy(petJpaEntity.id.asc())
                .fetch();
    }

    @Override
    public void updatePet(final Pet pet) {
        save(pet);
    }

    @Override
    public void deletePet(final Pet pet) {
        final var mappedToJpaEntity = petMapper.mapToPetEntity(pet);
        petJpaRepository.delete(mappedToJpaEntity);
    }

    @Override
    public void deleteAll(final List<Pet> pets) {
        petJpaRepository.deleteAllWithIds(
                pets.stream()
                        .map(pet -> pet.getId().value())
                        .toList()
        );
    }

}
