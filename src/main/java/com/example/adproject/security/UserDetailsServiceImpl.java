package com.example.adproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.adproject.model.User;
import com.example.adproject.repo.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo uRepo; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = uRepo.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found"); 
		}
		
		return new MyUserDetails(user); 
	}
}
