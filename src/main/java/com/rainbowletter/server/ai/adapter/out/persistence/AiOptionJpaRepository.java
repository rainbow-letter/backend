package com.rainbowletter.server.ai.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface AiOptionJpaRepository extends JpaRepository<AiOptionJpaEntity, Long> {

    Optional<AiOptionJpaEntity> findByPromptId(Long promptId);

}
