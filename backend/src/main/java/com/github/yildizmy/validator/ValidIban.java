package com.github.yildizmy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.github.yildizmy.common.Constants.FIELD_NOT_VALIDATED;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom constraint annotation for IngredientValidator
 */
@Documented
@Target({ElementType.FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {IbanValidator.class})
public @interface ValidIban {

    String message() default FIELD_NOT_VALIDATED;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
