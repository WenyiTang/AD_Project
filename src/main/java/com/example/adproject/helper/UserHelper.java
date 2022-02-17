package com.example.adproject.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserHelper {

	private String userId;
	private String username; 
	private String name; 
	private String profilePic;
	
	public UserHelper(String userId, String username, String name, String profilePic) {
		super(); 
		this.userId = userId; 
		this.username = username; 
		this.name = name; 
		this.profilePic = profilePic;
	}
}
