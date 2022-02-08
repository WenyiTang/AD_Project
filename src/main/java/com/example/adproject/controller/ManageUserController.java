package com.example.adproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.adproject.model.Role;
import com.example.adproject.model.User;
import com.example.adproject.repo.RoleRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.UserService;

@Controller
public class ManageUserController {

	@Autowired
	private UserService uService;
	
	@Autowired 
	private UserRepo uRepo; 
	
	@Autowired
	private RoleRepo rRepo; 

	@GetMapping("/create-account")
	public ModelAndView loadCreateAccountForm() {
		ModelAndView mav = new ModelAndView("create_account", "user", new User() {
		});
		return mav;
	}
	
	@PostMapping("/create-account")
	public ModelAndView createNewAccount(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		if (result.hasErrors()) {
			ModelAndView mav_error = new ModelAndView(); 
			String message_error = "Account creation failed. Please try again."; 
			mav_error.addObject("message_error", message_error); 
			mav_error.setViewName("create_account"); 
			return mav_error; 
		}
		
		
		// User's profilepic
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename()); 
		user.setProfilePic(fileName); 
		
		// User's password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		String encoderPassword = encoder.encode(user.getPassword()); 
		user.setPassword(encoderPassword); 
		
		user.setEnabled(true); 
		
		Role role = rRepo.findByType("USER"); 
		user.getRoles().add(role); 
		
		User newUser = uService.save(user); 
		
		String uploadDir = "./user-profilePic/" + newUser.getId();
		
		Path uploadPath = Paths.get(uploadDir); 
		
		// Saving user's profile pic into directory 
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath); 
		}
		
		try {
			InputStream inputStream = multipartFile.getInputStream(); 
			Path filePath = uploadPath.resolve(fileName); 
			Files.copy(inputStream, filePath ,StandardCopyOption.REPLACE_EXISTING); 
		} catch (IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName); 
		}
				
		
		ModelAndView mav = new ModelAndView("create_account", "user", new User() {}); 
		String message = "Account creation successful. Please login.";
		mav.addObject("message", message);
		mav.setViewName("create_account");
		return mav; 
	}
	
	@GetMapping("/create-account-admin") 
	public ModelAndView loadCreateAdminForm() {
		ModelAndView mav = new ModelAndView("create_account_admin", "user", new User() {}); 
		return mav; 
	}
	
	@PostMapping("/create-account-admin")
	public ModelAndView createNewAccount(@Valid @ModelAttribute("user") User user, BindingResult result) {
		
		if (result.hasErrors()) {
			ModelAndView mav_error = new ModelAndView(); 
			String message_error = "Account creation failed. Please try again."; 
			mav_error.addObject("message_error", message_error); 
			mav_error.setViewName("create_account_admin"); 
			return mav_error; 
		}
		
		// User's password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		String encoderPassword = encoder.encode(user.getPassword()); 
		user.setPassword(encoderPassword); 
		
		user.setEnabled(true); 
		
		user.setHeight(1.0);
		user.setWeight(1.0);
		
		Role role = rRepo.findByType("ADMIN");
		user.getRoles().add(role); 
		
		User newUser = uService.save(user); 
				
		ModelAndView mav = new ModelAndView("create_account_admin", "user", new User() {}); 
		String message = "Account creation successful. Please login.";
		mav.addObject("message", message);
		mav.setViewName("create_account_admin");
		return mav; 
	}
	
}
