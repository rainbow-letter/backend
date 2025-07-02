package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.Parameter.FirstLetter;
import com.rainbowletter.server.ai.application.domain.model.Parameter.LetterCount;
import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.out.CountLetterPort;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.in.RegenerateReplyUseCase;
import com.rainbowletter.server.reply.application.port.out.DeleteReplyPort;
import com.rainbowletter.server.reply.application.port.out.ExistsReplyPort;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyCommand;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class RegenerateReplyService implements RegenerateReplyUseCase {

    private final LoadPetPort loadPetPort;
    private final LoadLetterPort loadLetterPort;
    private final CountLetterPort countLetterPort;

    private final SaveReplyPort saveReplyPort;
    private final ExistsReplyPort existsReplyPort;
    private final DeleteReplyPort deleteReplyPort;
    private final GenerateReplyPort generateReplyPort;

    @Override
    public void regenerateReply(final RegenerateReplyCommand command) {
        if (existsReplyPort.existsByLetterId(command.letterId())) {
            deleteReplyPort.deleteByLetterId(command.letterId());
        }

        final Letter letter = loadLetterPort.loadLetterById(command.letterId());
        final Pet pet = loadPetPort.loadPetByIdAndUserId(
            letter.getPetId(),
            letter.getUserId()
        );
        final Long letterCount = countLetterPort.countByPetId(letter.getPetId());
        final boolean isFirstLetter = letterCount <= 1;

        final var generateReplyCommand = new GenerateReplyCommand(
            pet,
            letter,
            new LetterCount(letterCount),
            new FirstLetter(isFirstLetter)
        );
        final Reply reply = generateReplyPort.generate(generateReplyCommand);
        saveReplyPort.save(reply);
    }

}
