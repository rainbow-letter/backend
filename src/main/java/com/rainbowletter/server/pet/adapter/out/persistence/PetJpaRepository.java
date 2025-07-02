package com.rainbowletter.server.pet.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

interface PetJpaRepository extends JpaRepository<PetJpaEntity, Long> {

    Optional<PetJpaEntity> findByIdAndUserId(Long id, Long userId);

    List<PetJpaEntity> findAllByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM PetJpaEntity pet WHERE pet.id IN ?1")
    void deleteAllWithIds(List<Long> ids);

}
