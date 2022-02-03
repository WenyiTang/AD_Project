package com.example.adproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.adproject.model.FriendRequest;
import com.example.adproject.model.User;
import com.example.adproject.repo.FriendRequestRepo;
import com.example.adproject.repo.UserRepo;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo uRepo;

	@Autowired
	FriendRequestRepo fRepo;

	@Autowired
	FriendRequestService fService;

	@Override
	public List<User> findFriendsOf(User user) {
		ArrayList<User> friends = new ArrayList<User>();

		List<FriendRequest> acceptedRequests = fRepo.findAcceptedRequestsByUser(user);

		for (FriendRequest request : acceptedRequests) {
			if (request.getSender().getUsername().equals(user.getUsername())) {
				friends.add(request.getRecipient());
			} else {
				friends.add(request.getSender());
			}
		}

		return friends;
	}
	
	@Override 
	public User findUserByUsername(String username) {
		return uRepo.findByUsername(username); 
	}
	
	@Override
	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
		User user = uRepo.findByEmail(email); 
		if (user != null) {
			user.setResetPasswordToken(token);
			uRepo.save(user); 
		} else {
			throw new UserNotFoundException("No user associated with the following email: " + email); 
		}
	}
	
	@Override
	public User getByResetPasswordToken(String token) {
		return uRepo.findByResetPasswordToken(token); 
	}
	
	@Override
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		String encodedPassword = passwordEncoder.encode(newPassword); 
		user.setPassword(encodedPassword);
		user.setResetPasswordToken(null);
		uRepo.save(user); 
	}

}
