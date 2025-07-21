package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetInitiatedLetterJpaRepository extends JpaRepository<PetInitiatedLetter, Long> {
}
