package com.example.adproject.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = LogicalDimensionValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicalDimension {
	String message(); 
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { }; 
}
