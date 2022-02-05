package com.example.adproject.controller;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.adproject.model.User;


public class UserSession {
	

	private User user = null;

	
	public UserSession() {
		super();
	}
   //String sessionId,
	public UserSession( User user) {
		super();
		//this.sessionId = sessionId;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	


}