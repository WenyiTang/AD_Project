package com.example.adproject.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.UserService;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	UserService uService;

	@Autowired
	UserRepo uRepo;

	@Override
	public void initialize(UniqueEmail constraintAnnotation) {

	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (uRepo == null) {
			return true;
		}

		return uRepo.findByEmail(email) == null;
	}

}
