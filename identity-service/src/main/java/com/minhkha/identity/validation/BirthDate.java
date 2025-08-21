package com.minhkha.identity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {
    int min() default 18;
    String message() default "Tuổi không đủ yêu cầu";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
