package com.rainbowletter.server.faq.application.port.in.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class FaqSummaryValidator implements ConstraintValidator<FaqSummary, String> {

    private static final int MAX_SUMMARY_LENGTH = 30;

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return value.length() <= MAX_SUMMARY_LENGTH;
    }

}
