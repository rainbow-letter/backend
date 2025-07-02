package com.rainbowletter.server.letter.application.port.in.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class LetterSummaryValidator implements ConstraintValidator<LetterSummary, String> {

    private static final int MAX_SUMMARY_LENGTH = 20;

    @Override
    public boolean isValid(final String value,
        final ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return value.length() <= MAX_SUMMARY_LENGTH;
    }

}
