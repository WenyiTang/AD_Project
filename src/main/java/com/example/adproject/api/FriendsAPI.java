package com.example.adproject.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adproject.helper.UserSummary;
import com.example.adproject.model.User;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.FriendRequestService;
import com.example.adproject.service.UserService;

@RestController
@RequestMapping("/api/friends")
public class FriendsAPI {

	@Autowired
	UserService uService; 
	
	@Autowired
	UserRepo uRepo; 
	
	@Autowired 
	FriendRequestService fService; 
	
	@GetMapping("/all")
	public List<UserSummary> findAllFriends(@RequestParam("username") String username) {
		User user = uService.findUserByUsername(username); 
		List<User> users = uService.findFriendsOf(user); 
		List<UserSummary> friends = new ArrayList<>(); 
		
		for (User u : users) {
			UserSummary friend = new UserSummary(u.getId(), u.getUsername(), u.getName(), u.getImagePath());
			friends.add(friend); 
		}
		return friends; 
	}
	
//	@GetMapping("/find")
//	public List<UserSummary> findUserByUsername(String username) {
//		User 
//	}
}
