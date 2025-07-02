package com.rainbowletter.server.sharedletter.application.domain.service;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.in.dto.PetSimpleSummary;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.sharedletter.adapter.in.web.dto.CreateSharedLetterRequest;
import com.rainbowletter.server.sharedletter.adapter.in.web.dto.RetrieveSharedLetterByUserIdRequest;
import com.rainbowletter.server.sharedletter.adapter.in.web.dto.RetrieveSharedLetterRequest;
import com.rainbowletter.server.sharedletter.adapter.in.web.dto.SharedLetterResponse;
import com.rainbowletter.server.sharedletter.adapter.out.persistence.SharedLetterJpaRepository;
import com.rainbowletter.server.sharedletter.adapter.out.persistence.SharedLetterPersistenceAdapter;
import com.rainbowletter.server.sharedletter.application.domain.model.RecipientType;
import com.rainbowletter.server.sharedletter.application.domain.model.SharedLetter;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedLetterService {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final SharedLetterPersistenceAdapter sharedLetterPersistenceAdapter;
    private final SharedLetterJpaRepository sharedLetterJpaRepository;


    @Transactional
    public SharedLetterResponse createSharedLetter(Long petId, String email, CreateSharedLetterRequest request) {
        final User user = loadUserPort.loadUserByEmail(email);
        final Pet pet = loadPetPort.loadPetByIdAndUserId(new Pet.PetId(petId), user.getId());

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        if (sharedLetterJpaRepository.existsByPetIdAndCreatedAtAfter(petId, startOfDay)) {
            throw new RainbowLetterException(
                "이미 오늘 무지개에 걸린 편지를 작성하셨습니다.",
                "petId: [%d]".formatted(pet.getId().value())
            );
        }

        SharedLetter sharedLetter = SharedLetter.builder()
            .recipientType(request.recipientType())
            .petId(pet.getId().value())
            .userId(user.getId().value())
            .content(request.content())
            .build();
        SharedLetter savedSharedLetter = sharedLetterJpaRepository.save(sharedLetter);

        return new SharedLetterResponse(
            savedSharedLetter.getId(),
            savedSharedLetter.getContent(),
            savedSharedLetter.getRecipientType(),
            new PetSimpleSummary(
                pet.getId().value(),
                pet.getName(),
                pet.getImage()
            )
        );
    }

    @Transactional(readOnly = true)
    public List<SharedLetterResponse> retrieveByUser(String email, RetrieveSharedLetterByUserIdRequest request) {
        final User user = loadUserPort.loadUserByEmail(email);

        return sharedLetterPersistenceAdapter.retrieveByUser(
            user.getId().value(),
            request.startDate(),
            request.endDate()
        );
    }

    @Transactional(readOnly = true)
    public List<SharedLetterResponse> retrieve(String email, RetrieveSharedLetterRequest request) {
        final User user = loadUserPort.loadUserByEmail(email);

        return sharedLetterPersistenceAdapter.retrieve(
            user.getId().value(),
            request.after(),
            request.startDate(),
            request.endDate(),
            request.limit(),
            request.randomSort()
        );
    }

    public List<SharedLetterResponse> retrieveSampleLetters() {
        return List.of(
            new SharedLetterResponse(
                0L,
                "엄마를 만나\n행복했니?\n엄마는 미키 덕분에\n정말 행복했어",
                RecipientType.PET,
                new PetSimpleSummary(0L, "미키", "")
            ),
            new SharedLetterResponse(
                0L,
                "내가 보고 싶을 때\n밤하늘을 봐줘\n별이 되어 누나를\n지켜보고 있으니까!",
                RecipientType.OWNER,
                new PetSimpleSummary(0L, "우주", "")
            ),
            new SharedLetterResponse(
                0L,
                "코코야 엄마가\n항상 사랑해\n다음에도 내\n아들이 되어줘",
                RecipientType.PET,
                new PetSimpleSummary(0L, "코코", "")
            ),
            new SharedLetterResponse(
                0L,
                "나는 항상 언니\n마음 속에 있다는 거\n잊지 마! 항상 언니를 응원해.",
                RecipientType.OWNER,
                new PetSimpleSummary(0L, "무지", "")
            )
        );
    }

}