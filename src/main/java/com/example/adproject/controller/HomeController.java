package com.example.adproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.UserService;


@Controller
public class HomeController {
	//not used
	
	
	@Autowired
	private MealEntryService mService;
	
	@Autowired
	private MealEntryRepo mRepo;
	
	@Autowired
	private UserService uService;
	
	@RequestMapping("/index")
	public String getHomePage(Model model, Principal principal) {
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		long countOnT = mRepo.findEntryByAuthor(userId).stream()
						.filter(x->x.getTrackScore()==1)
						.count();
		long countOffT = mRepo.findEntryByAuthor(userId).stream()
						.filter(x->x.getTrackScore() == 0)
						.count();
		
		model.addAttribute("onTrack", countOnT);
		model.addAttribute("offTrack", countOffT);
		return "index";
	}
	

}
