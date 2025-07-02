package com.rainbowletter.server.log.adapter.out.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserAgentLogJpaRepository extends JpaRepository<UserAgentLogJpaEntity, Long> {

}
