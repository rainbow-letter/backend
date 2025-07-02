package com.rainbowletter.server.letter.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

interface LetterJpaRepository extends JpaRepository<LetterJpaEntity, Long> {

    Optional<LetterJpaEntity> findByIdAndUserId(Long id, Long userId);

    List<LetterJpaEntity> findAllByPetId(Long petId);

    @Modifying
    @Query("DELETE FROM LetterJpaEntity letter WHERE letter.id IN ?1")
    void deleteAllWithIds(List<Long> ids);

}
