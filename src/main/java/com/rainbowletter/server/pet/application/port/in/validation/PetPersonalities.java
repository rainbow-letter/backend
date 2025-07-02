package com.rainbowletter.server.pet.application.port.in.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PetPersonalitiesValidator.class)
public @interface PetPersonalities {

    String message() default "반려 동물의 성격은 최대 3개까지만 선택 가능합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
