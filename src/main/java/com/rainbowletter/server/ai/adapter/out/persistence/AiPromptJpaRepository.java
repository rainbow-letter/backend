package com.rainbowletter.server.ai.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface AiPromptJpaRepository extends JpaRepository<AiPromptJpaEntity, Long> {

}
