package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.User;

import org.springframework.stereotype.Component;

@Component
public interface UserService {

    List<User> findFriendsOf(User user);
    
    User findUserByUsername(String username); 
    
    User findUserByEmail(String email); 
    
    void updateResetPasswordToken(String token, String email) throws UserNotFoundException; 
    
    User getByResetPasswordToken(String token);
    
    void updatePassword(User user, String newPassword); 
   
    User save(User user); 
}
