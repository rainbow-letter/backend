package com.rainbowletter.server.temporary.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.UuidHolder;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.temporary.application.domain.model.Temporary;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryStatus;
import com.rainbowletter.server.temporary.application.port.in.CreateTemporaryCommand;
import com.rainbowletter.server.temporary.application.port.in.CreateTemporaryUseCase;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryCreateResponse;
import com.rainbowletter.server.temporary.application.port.out.ExistsTemporaryPort;
import com.rainbowletter.server.temporary.application.port.out.SaveTemporaryPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreateTemporaryService implements CreateTemporaryUseCase {

    private final UuidHolder uuidHolder;
    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final ExistsTemporaryPort existsTemporaryPort;
    private final SaveTemporaryPort saveTemporaryPort;

    @Override
    public TemporaryCreateResponse createTemporary(final CreateTemporaryCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.getEmail());
        if (existsTemporaryPort.existsEmailAndPetId(command.getEmail(), command.getPetId())) {
            throw new RainbowLetterException("exists.temporary");
        }

        final Pet pet = loadPetPort.loadPetByIdAndUserId(command.getPetId(), user.getId());
        final LocalDateTime currentTime = LocalDateTime.now();
        final Temporary temporary = Temporary.withoutId(
            user.getId(),
            pet.getId(),
            uuidHolder.generate(),
            command.getContent(),
            TemporaryStatus.SAVE,
            currentTime,
            currentTime
        );
        return TemporaryCreateResponse.from(saveTemporaryPort.save(temporary));
    }

}
