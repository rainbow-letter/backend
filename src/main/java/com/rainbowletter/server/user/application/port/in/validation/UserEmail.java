package com.rainbowletter.server.user.application.port.in.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserEmailValidator.class)
public @interface UserEmail {

    String message() default "유효하지 않은 이메일 형식입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
