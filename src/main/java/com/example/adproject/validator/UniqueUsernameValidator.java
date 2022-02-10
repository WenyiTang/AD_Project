package com.example.adproject.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.UserService;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	UserService uService;
	
	@Autowired 
	UserRepo uRepo; 
	
	@Override 
	public void initialize(UniqueUsername constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if (uRepo == null) {
			return true; 
		}
	
		return uRepo.findByUsername(username) == null; 
	} 
	
	
	
	

}
