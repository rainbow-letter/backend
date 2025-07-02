package com.rainbowletter.server.letter.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.out.SaveLetterPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class SaveLetterPersistenceAdapter implements SaveLetterPort {

    private final LetterMapper letterMapper;
    private final LetterJpaRepository letterJpaRepository;

    @Override
    public Letter save(final Letter letter) {
        final var mappedToJpaEntity = letterMapper.mapToJpaEntity(letter);
        return letterMapper.mapToDomain(letterJpaRepository.save(mappedToJpaEntity));
    }

}
