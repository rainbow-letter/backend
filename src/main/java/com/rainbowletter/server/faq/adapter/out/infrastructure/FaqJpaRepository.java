package com.rainbowletter.server.faq.adapter.out.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

interface FaqJpaRepository extends JpaRepository<FaqJpaEntity, Long> {

}
