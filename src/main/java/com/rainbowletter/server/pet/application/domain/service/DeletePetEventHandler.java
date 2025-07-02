package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.log.application.port.in.LogEventCommand;
import com.rainbowletter.server.log.application.port.in.LogEventUseCase;
import com.rainbowletter.server.pet.application.port.in.DeletePetUseCase;
import com.rainbowletter.server.pet.application.port.in.DeletePetUseCase.DeletePetByUserIdCommand;
import com.rainbowletter.server.user.application.domain.model.DeleteUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
class DeletePetEventHandler {

    private final LogEventUseCase logEventUseCase;
    private final DeletePetUseCase deletePetUseCase;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void fromUserDelete(final DeleteUserEvent event) {
        final var command = new DeletePetByUserIdCommand(event.user().getId());
        deletePetUseCase.deletePetsByUserId(command, pet -> logEventUseCase.successLog(
            new LogEventCommand(
                pet.getId().value(),
                pet.getUserId().value(),
                "PET",
                "DELETE",
                "사용자 탈퇴로 인해 반려동물 삭제"
            )
        ));
    }

}
