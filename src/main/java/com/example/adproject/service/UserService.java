package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.User;

import org.springframework.stereotype.Component;

@Component
public interface UserService {

    List<User> findFriendsOf(User user);
    
}
