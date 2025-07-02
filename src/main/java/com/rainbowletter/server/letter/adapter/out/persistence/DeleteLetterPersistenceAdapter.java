package com.rainbowletter.server.letter.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.out.DeleteLetterPort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class DeleteLetterPersistenceAdapter implements DeleteLetterPort {

    private final LetterMapper letterMapper;
    private final LetterJpaRepository letterJpaRepository;

    @Override
    public void delete(final Letter letter) {
        final var mappedToJpaEntity = letterMapper.mapToJpaEntity(letter);
        letterJpaRepository.delete(mappedToJpaEntity);
    }

    @Override
    public void deleteAll(final List<Letter> letters) {
        final List<Long> ids = letters.stream()
            .map(Letter::getId)
            .map(LetterId::value)
            .toList();
        letterJpaRepository.deleteAllWithIds(ids);
    }

}
