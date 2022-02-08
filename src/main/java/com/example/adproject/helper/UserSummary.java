package com.example.adproject.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSummary {

	private Integer userId; 
	private String username; 
	private String name; 
	private String profilePicPath; 
	
	public UserSummary(Integer userId, String username, String name, String profilePicPath) {
		super(); 
		this.userId = userId; 
		this.username = username; 
		this.name = name; 
		this.profilePicPath = profilePicPath; 
	}
}
