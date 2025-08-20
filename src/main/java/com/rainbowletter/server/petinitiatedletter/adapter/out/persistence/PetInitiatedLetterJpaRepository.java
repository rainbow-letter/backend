package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PetInitiatedLetterJpaRepository extends JpaRepository<PetInitiatedLetter, Long> {
    List<PetInitiatedLetter> findAllByStatusAndCreatedAtBetween(
        PetInitiatedLetterStatus status, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
