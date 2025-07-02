package com.rainbowletter.server.sharedletter.adapter.out.persistence;

import com.rainbowletter.server.sharedletter.application.domain.model.SharedLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SharedLetterJpaRepository extends JpaRepository<SharedLetter, Long> {
    boolean existsByPetIdAndCreatedAtAfter(Long petId, LocalDateTime startOfDay);
}