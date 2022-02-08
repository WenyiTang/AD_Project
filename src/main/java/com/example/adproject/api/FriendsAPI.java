package com.example.adproject.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	@GetMapping("/find")
	public List<UserSummary> searchFriendByUsername(@RequestParam("query") String query, @RequestParam("username") String username) {
		User user = uService.findUserByUsername(username);
		List<User> users = uService.findFriendsOf(user);
		List<UserSummary> result = new ArrayList<>();

		List<User> list = users.stream().filter(x -> x.getUsername().contains(query)).collect(Collectors.toList());
		for (User u : list) {
			UserSummary friend = new UserSummary(u.getId(), u.getUsername(), u.getName(), u.getImagePath());
			result.add(friend);
		}
		return result;
	}

	@GetMapping("/find_users")
	public List<UserSummary> getAllUsers(@RequestParam("query") String query, @RequestParam("username") String username) {
		User user = uService.findUserByUsername(username);
		List<User> friends = uService.findFriendsOf(user);

		List<User> all_users = uRepo.findAllUsersOnly();
		List<User> non_friends = all_users.stream().filter(x -> !friends.contains(x)).collect(Collectors.toList());

		// Remove self-user
		non_friends.remove(user);
		List<User> queryResult = non_friends.stream().filter(x -> x.getUsername().contains(query)).collect(Collectors.toList());

		List<UserSummary> result = new ArrayList<>();
		for (User u : queryResult) {
			UserSummary userSummary = new UserSummary(u.getId(), u.getUsername(), u.getName(), u.getImagePath());
			result.add(userSummary);
		}

		return result;
	}
}
