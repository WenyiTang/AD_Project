package com.example.adproject.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.adproject.model.User;



@Component
public class ProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User) target;
		ValidationUtils.rejectIfEmpty(errors, "name", "error.user.name.empty", "Name is required");
		ValidationUtils.rejectIfEmpty(errors, "dateOfBirth", "error.user.dateOfBirth.empty");
		ValidationUtils.rejectIfEmpty(errors, "height", "error.user.height.empty");
		ValidationUtils.rejectIfEmpty(errors, "weight", "error.user.weight.empty");
		System.out.println(u.toString());
	}

}
