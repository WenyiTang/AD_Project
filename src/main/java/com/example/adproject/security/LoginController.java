package com.example.adproject.security;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.UserService;

@Controller
public class LoginController {
	
	
	@Autowired
	private GoalRepo gRepo;
	
	@Autowired
	private MealEntryRepo mRepo;
	
	@Autowired
	private UserService uService;

	@GetMapping("")
	public String viewHomePage(Model model, Principal principal) {
		Integer userId = uService.findUserByUsername(principal.getName()).getId();
		long countOnT = mRepo.findEntryByAuthor(userId).stream()
						.filter(x->x.getTrackScore()==1)
						.count();
		long countOffT = mRepo.findEntryByAuthor(userId).stream()
						.filter(x->x.getTrackScore() == 0)
						.count();
		
		Goal currentGoal= gRepo.findCurrentGoal(userId);
		List<Goal> completedGoal = gRepo.findCompletedGoals(userId);
		List<MealEntry> allEntries = mRepo.findEntryByAuthor(userId);
		

		model.addAttribute("onTrack", countOnT);
		model.addAttribute("offTrack", countOffT);
		model.addAttribute("currentGoal", currentGoal);
		model.addAttribute("completedGoal", completedGoal);
		model.addAttribute("allEntries", allEntries);
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
	
	@GetMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
}
