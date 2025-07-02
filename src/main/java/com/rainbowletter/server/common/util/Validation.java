package com.rainbowletter.server.common.util;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validation {

    private static final Validator validator;

    static {
        try (final var factory = buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public static <T> void validate(final T self) {
        final Set<ConstraintViolation<T>> violations = validator.validate(self);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
