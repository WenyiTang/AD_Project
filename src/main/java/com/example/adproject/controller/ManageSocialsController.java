package com.example.adproject.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.adproject.model.User;
import com.example.adproject.service.FriendRequestService;
import com.example.adproject.service.UserService;

@Controller
@RequestMapping("/socials")
public class ManageSocialsController {

	@Autowired 
	FriendRequestService fService; 
	
	@Autowired
	UserService uService; 
	
	@GetMapping("/manage")
	public String loadManageSocialsPage(Model model, Principal principal) {
		User user = uService.findUserByUsername(principal.getName()); 
		model.addAttribute("user", user); 
		
		return "manage_socials"; 
	}
	
	@GetMapping("/add")
	public String loadAddFriendPage(Model model, Principal principal) {
		User user = uService.findUserByUsername(principal.getName()); 
		model.addAttribute("user", user); 
		return "add_friend"; 
	}
	
	@GetMapping("/friend_requests")
	public String loadFriendRequestsPage(Model model, Principal principal) {
		User user = uService.findUserByUsername(principal.getName()); 
		model.addAttribute("user", user); 
		return "friend_requests"; 
	}
	
	@GetMapping("/friend/{username}")
	public ModelAndView loadFriendDetailPage(@PathVariable("username") String username) {
		User user = uService.findUserByUsername(username);
		ModelAndView mav = new ModelAndView(); 
		
		mav.addObject("friend", user); 
		mav.setViewName("friend_details"); 
		return mav; 
	}
}
