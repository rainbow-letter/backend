package com.rainbowletter.server.user.application.port.in.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class UserPhoneNumberValidator implements ConstraintValidator<UserPhoneNumber, String> {

    private static final String PHONE_NUMBER_REGEX = "^01[016789]\\d{7,8}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return PHONE_NUMBER_PATTERN.matcher(value).matches();
    }

}
