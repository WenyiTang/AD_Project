package com.example.adproject.login;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.adproject.model.User;
import com.example.adproject.service.UserService;

@Controller
@RequestMapping
public class LoginController {

	@Autowired
	UserService uService; 
	
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login"); 
	}
	
	@RequestMapping("/dashboard")
	public String userDashboard(Model model, Principal principal) {
		User user = uService.findUserByUsername(principal.getName()); 
		model.addAttribute("user", user);
		return "dashboard"; 
	}
	
	@RequestMapping("/adminDashboard")
	public String adminDashboard() {
		return "adminDashboard"; 
	}
}
