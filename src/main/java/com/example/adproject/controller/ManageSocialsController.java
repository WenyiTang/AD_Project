package com.example.adproject.controller;

import java.security.Principal;

import com.example.adproject.model.FriendRequest;
import com.example.adproject.repo.FriendRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
	FriendRequestRepo fRepo;
	
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

	@RequestMapping("/friend/delete/{username}")
	public String deleteFriend(@PathVariable String username, Model model, Principal principal) {
		User user = uService.findUserByUsername(principal.getName());
		User friend = uService.findUserByUsername(username);

		FriendRequest friendShip = fService.findAcceptedRequestsByUsers(user, friend);
		fService.deleteRequest(friendShip);

		model.addAttribute("user", user);
		String message = "You are no longer friends with " + friend.getName();
		model.addAttribute("message", message);
		return "manage_socials";
	}

	@RequestMapping("/friend/add/{username}")
	public String addFriend(@PathVariable String username, Model model, Principal principal) {
		User user = uService.findUserByUsername(principal.getName());
		User friend = uService.findUserByUsername(username);

		FriendRequest request = new FriendRequest(user, friend);
		fRepo.saveAndFlush(request);

		model.addAttribute("user", user);
		String message = "Friend request sent to " + friend.getName() + ".";
		model.addAttribute("message", message);
		return "add_friend";
	}
}
