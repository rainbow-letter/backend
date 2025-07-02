package com.rainbowletter.server.reply.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<ReplyJpaEntity, Long> {

    boolean existsByLetterId(Long letterId);

    void deleteByLetterId(Long letterId);

}
