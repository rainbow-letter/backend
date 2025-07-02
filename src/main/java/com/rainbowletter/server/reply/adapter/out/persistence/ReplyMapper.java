package com.rainbowletter.server.reply.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import java.util.Objects;

@PersistenceMapper
class ReplyMapper {

    Reply mapToDomain(final ReplyJpaEntity jpaEntity) {
        return Reply.withId(
            new ReplyId(jpaEntity.getId()),
            new PetId(jpaEntity.getPetId()),
            new LetterId(jpaEntity.getLetterId()),
            jpaEntity.getSummary(),
            jpaEntity.getContent(),
            jpaEntity.getPromptA(),
            jpaEntity.getPromptB(),
            jpaEntity.getPromptType(),
            jpaEntity.isInspection(),
            jpaEntity.getInspectionTime(),
            jpaEntity.getStatus(),
            jpaEntity.getSubmitTime(),
            jpaEntity.getReadStatus(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }

    ReplyJpaEntity mapToJpaEntity(final Reply domain) {
        return new ReplyJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getPetId().value(),
            domain.getLetterId().value(),
            domain.getSummary(),
            domain.getContent(),
            domain.getPromptA(),
            domain.getPromptB(),
            domain.getPromptType(),
            domain.isInspection(),
            domain.getInspectionTime(),
            domain.getStatus(),
            domain.getSubmitTime(),
            domain.getReadStatus()
        );
    }

}
