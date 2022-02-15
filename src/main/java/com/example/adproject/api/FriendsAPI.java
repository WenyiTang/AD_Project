package com.example.adproject.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import com.example.adproject.model.FriendRequest;
import com.example.adproject.repo.FriendRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.adproject.helper.UserHelper;
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

	@Autowired
	FriendRequestRepo fRepo;

	private List<UserHelper> convertUserToUserSummary(List<User> users) {
		List<UserHelper> friends = new ArrayList<>();
		for (User u : users) {
			UserHelper friend = new UserHelper(u.getId().toString(), u.getUsername(), u.getName(), u.getProfilePic());
			friends.add(friend);
		}
		return friends;
	}
	
	@GetMapping("/all")
	public List<UserHelper> findAllFriends(@RequestParam("username") String username) {
		User user = uService.findUserByUsername(username); 
		List<User> users = uService.findFriendsOf(user); 
		return convertUserToUserSummary(users);
	}
	
	@GetMapping("/find")
	public List<UserHelper> searchFriendByUsername(@RequestParam("query") String query, @RequestParam("username") String username) {
		User user = uService.findUserByUsername(username);
		List<User> users = uService.findFriendsOf(user);
		List<UserHelper> result = new ArrayList<>();

		List<User> list = users.stream().filter(x -> x.getUsername().contains(query)).collect(Collectors.toList());
		return convertUserToUserSummary(list);
	}

	@GetMapping("/find_users")
	public List<UserHelper> queryUsersByUsername(@RequestParam("query") String query, @RequestParam("username") String username) {
		User user = uService.findUserByUsername(username);
		List<User> friends = uService.findFriendsOf(user);

		List<User> all_users = uRepo.findAllUsersOnly();
		List<User> pending_users = fService.findPendingUsersByUser(user);
		List<User> non_friends = all_users.stream().filter(x -> !friends.contains(x)).collect(Collectors.toList());
		List<User> non_friends_pending = non_friends.stream().filter(x -> !pending_users.contains(x)).collect(Collectors.toList());

		// Remove self-user & pending users
		non_friends_pending.remove(user);
		List<User> queryResult = non_friends_pending.stream().filter(x -> x.getUsername().contains(query)).collect(Collectors.toList());

		return convertUserToUserSummary(queryResult);
	}

	@GetMapping("/requests")
	public List<UserHelper> findPendingFriendRequests(@RequestParam("username") String username, @RequestParam("sent") boolean sent) {
		User user = uService.findUserByUsername(username);
		List<UserHelper> pendingFriends = new ArrayList<>();

		if (sent) {
			List<FriendRequest> sent_requests = fRepo.findPendingRequestsBySender(user);
			List<User> users = sent_requests.stream().map(x -> x.getRecipient()).collect(Collectors.toList());
			return convertUserToUserSummary(users);
		} else {
			List<FriendRequest> received_requests = fRepo.findPendingRequestsByRecipient(user);
			List<User> users = received_requests.stream().map(x -> x.getSender()).collect(Collectors.toList());
			return convertUserToUserSummary(users);
		}
	}

	@RequestMapping("/request/process")
	public Map<String, String> processFriendRequest(@RequestParam("username") String username_this, @RequestParam("sender") String username_other, @RequestParam("action") String action) {

		Map<String, String> responseMap = new HashMap<>();

		if (!action.equals("delete")) {
			User recipient = uService.findUserByUsername(username_this);
			User sender = uService.findUserByUsername(username_other);

			FriendRequest request = fService.findRequest(sender, recipient);

			if (action.equals("accept")) {
				fService.acceptRequest(request.getId());
				String msg = "You are now friends with " + username_other;
				responseMap.put("message", msg);
			} else {
				fService.rejectRequest(request);
				String msg = "Rejected friend request from" + username_other;
				responseMap.put("message", msg);
			}
		} else {
			User sender = uService.findUserByUsername(username_this);
			User recipient = uService.findUserByUsername(username_other);

			FriendRequest request = fService.findRequest(sender, recipient);

			fService.rejectRequest(request);
			String msg = "Friend request to " + username_other + " has been deleted";
			responseMap.put("message", msg);
		}

		return responseMap;
	}

	@GetMapping(value ="/profilePic", produces = MediaType.IMAGE_JPEG_VALUE, params = {"fileName", "userId"})
	public @ResponseBody byte[] getProfilePic(@RequestParam String fileName, @RequestParam String userId) throws IOException {
		String path = "images/" + userId + "/" + fileName;
		File file = new File(path);
		byte[] fileContent = Files.readAllBytes(file.toPath());
		return fileContent;
	}
}
