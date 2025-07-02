package com.rainbowletter.server.pet.application.port.in.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class PetPersonalitiesValidator
    implements ConstraintValidator<PetPersonalities, List<String>> {

    private static final int MAX_PERSONALITY_SIZE = 3;

    @Override
    public boolean isValid(final List<String> strings, final ConstraintValidatorContext context) {
        return strings.size() <= MAX_PERSONALITY_SIZE;
    }

}
