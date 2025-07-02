package com.rainbowletter.server.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class QueryDslConfig {

    private final EntityManager entityManager;

    @Bean
    JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
