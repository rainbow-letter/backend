package com.rainbowletter.server.petinitiatedletter.adapter.out.persistence;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.UserPetInitiatedLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPetInitiatedLetterJpaRepository extends JpaRepository<UserPetInitiatedLetter, Long> {
    boolean existsByUserIdAndPetId(Long userId, Long petId);
    void deleteByUserIdAndPetId(Long userId, Long petId);
}
