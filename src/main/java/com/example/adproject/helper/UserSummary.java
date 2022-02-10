package com.example.adproject.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSummary {

	private Integer userId; 
	private String username; 
	private String name; 
	private String profilePic;
	
	public UserSummary(Integer userId, String username, String name, String profilePic) {
		super(); 
		this.userId = userId; 
		this.username = username; 
		this.name = name; 
		this.profilePic = profilePic;
	}
}
