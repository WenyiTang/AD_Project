package com.example.adproject.security;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.adproject.model.Role;
import com.example.adproject.model.User;
import com.example.adproject.service.ReportService;
import com.example.adproject.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	ReportService rService;
	
	@Autowired
	UserService uService;

	@GetMapping("")
	public String viewHomePage(Model model, Principal principal) {
		//if user is admin
		User loggedin = uService.findUserByUsername(principal.getName());
		Set<Role> roles = loggedin.getRoles();
		for (Role r : roles) {
			if (r.getType().equalsIgnoreCase("ADMIN")) {
				Integer reportCount = rService.findPendingNProgressReports(loggedin).size();
				model.addAttribute("reportCount", reportCount);
				break;
			}
		}
		
		model.addAttribute("title", "Food Diary - Home"); 
		return "index"; 
	}
	
	@GetMapping("/login")
	public String loadLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login"; 
		}
		
		return "redirect:/"; 
	}
}
