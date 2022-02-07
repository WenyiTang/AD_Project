package com.example.adproject.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LogicalDimensionValidator implements ConstraintValidator<LogicalDimension, Double>{

	@Override 
	public void initialize(LogicalDimension constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Double dimension, ConstraintValidatorContext context) {
		if (dimension == null) {
			return true; 
		}
		return (dimension > 0) ? true : false; 
	} 
}
