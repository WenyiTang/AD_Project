package com.example.adproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.adproject.service.MealEntryService;


@Controller
public class HomeController {
	
	@Autowired
	private MealEntryService service;
	
	@RequestMapping("/index")
	public String getHomePage(Model model) {
		long countOnT = service.getAllEntry().stream()
						.filter(x->x.getTrackScore()==1)
						.count();
		long countOffT = service.getAllEntry().stream()
						.filter(x->x.getTrackScore() == 0)
						.count();
		
		model.addAttribute("onTrack", countOnT);
		model.addAttribute("offTrack", countOffT);
		return "index";
	}
	
//	@RequestMapping("/dashboard")
//	public String userDashboard(Model model, Principal principal) {
//		Integer userId = uService.findUserByUsername(principal.getName()).userId;
//		Employee employee = eService.findByUserId(userId);
//		model.addAttribute("employee",employee);
//		return "dashboard";
//	}

}
