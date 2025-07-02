package com.rainbowletter.server.user.application.port.in.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public class UserPasswordValidator implements ConstraintValidator<UserPassword, String> {

    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)[\\w!@#$%^&*()\\-=+`~]{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(value).matches();
    }

}
