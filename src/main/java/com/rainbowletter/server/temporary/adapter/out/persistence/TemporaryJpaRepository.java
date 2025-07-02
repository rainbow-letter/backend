package com.rainbowletter.server.temporary.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface TemporaryJpaRepository extends JpaRepository<TemporaryJpaEntity, Long> {

}
