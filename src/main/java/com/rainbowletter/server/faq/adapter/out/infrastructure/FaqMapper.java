package com.rainbowletter.server.faq.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.faq.application.domain.model.Faq;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import java.util.Objects;

@PersistenceMapper
class FaqMapper {

    Faq mapToDomain(final FaqJpaEntity jpaEntity) {
        return Faq.withId(
            new FaqId(jpaEntity.getId()),
            jpaEntity.getSummary(),
            jpaEntity.getDetail(),
            jpaEntity.isVisibility(),
            jpaEntity.getSequence(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }

    FaqJpaEntity mapToJpaEntity(final Faq domain) {
        return new FaqJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getSummary(),
            domain.getDetail(),
            domain.isVisibility(),
            domain.getSequence()
        );
    }

}
