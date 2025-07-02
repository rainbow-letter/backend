package com.rainbowletter.server.temporary.adapter.in.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TemporaryContentValidator implements ConstraintValidator<TemporaryContent, String> {

    private static final int MAX_CONTENT_LENGTH = 1000;

    @Override
    public boolean isValid(final String value,
        final ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return false;
        }
        return value.length() <= MAX_CONTENT_LENGTH;
    }

}
