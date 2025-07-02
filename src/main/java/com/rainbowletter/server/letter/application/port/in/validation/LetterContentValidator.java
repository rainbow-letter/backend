package com.rainbowletter.server.letter.application.port.in.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class LetterContentValidator implements ConstraintValidator<LetterContent, String> {

    private static final int MAX_CONTENT_LENGTH = 1000;

    @Override
    public boolean isValid(final String value,
        final ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return value.length() <= MAX_CONTENT_LENGTH;
    }

}
