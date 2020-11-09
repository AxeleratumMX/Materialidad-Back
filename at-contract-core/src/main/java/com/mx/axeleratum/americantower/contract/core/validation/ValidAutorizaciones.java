package com.mx.axeleratum.americantower.contract.core.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValidAutorizacionesValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidAutorizaciones {
    String message() default "Debe seleccionar al menos una autorización en alguna sección";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
}
