package com.rainbowletter.server.ai.application.domain.model;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Parameter {
    PET_NAME(Pet.class) {
        @Override
        public String value(final Object instance) {
            Parameter.validateInstanceType(instance, Pet.class);
            return ((Pet) instance).getName();
        }
    },
    PET_OWNER(Pet.class) {
        @Override
        public String value(final Object instance) {
            Parameter.validateInstanceType(instance, Pet.class);
            return ((Pet) instance).getOwner();
        }
    },
    PET_SPECIES(Pet.class) {
        @Override
        public String value(final Object instance) {
            Parameter.validateInstanceType(instance, Pet.class);
            return ((Pet) instance).getSpecies();
        }
    },
    LETTER_CONTENT(Letter.class) {
        @Override
        public String value(final Object instance) {
            Parameter.validateInstanceType(instance, Letter.class);
            return ((Letter) instance).getContent();
        }
    },
    LETTER_COUNT(LetterCount.class) {
        @Override
        public String value(final Object instance) {
            Parameter.validateInstanceType(instance, LetterCount.class);
            return String.valueOf(instance);
        }
    },
    FIRST_LETTER(FirstLetter.class) {
        @Override
        public String value(final Object instance) {
            Parameter.validateInstanceType(instance, FirstLetter.class);
            return String.valueOf(instance);
        }
    };

    private final Class<?> clazz;

    private static void validateInstanceType(final Object instance, final Class<?> clazz) {
        if (!(clazz.isInstance(instance))) {
            throw new RainbowLetterException("invalid.ai.parameter.instance");
        }
    }

    public static Parameter get(final String name) {
        return Arrays.stream(Parameter.values())
            .filter(parameter -> parameter.name().equals(name))
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("not.found.ai.parameter", name));
    }

    public abstract String value(Object instance);

    public record LetterCount(Long count) {

    }

    public record FirstLetter(Boolean isFirstLetter) {

    }

}
