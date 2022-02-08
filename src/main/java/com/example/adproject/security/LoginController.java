package com.example.adproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.service.ReportService;

@Controller
public class LoginController {
	
	@Autowired
	ReportService rService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		model.addAttribute("title", "Food Diary - Home"); 
		Integer reportCount = rService.findReportsByStatus(ReportEnum.PENDING).size();
		model.addAttribute("reportCount", reportCount);
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
