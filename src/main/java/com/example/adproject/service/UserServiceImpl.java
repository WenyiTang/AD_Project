package com.example.adproject.service;

import java.util.ArrayList;
import java.util.List;

import com.example.adproject.model.FriendRequest;
import com.example.adproject.model.User;
import com.example.adproject.repo.FriendRequestRepo;
import com.example.adproject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        for(FriendRequest request : acceptedRequests){
            if(request.getSender().getUsername().equals(user.getUsername())){
                friends.add(request.getRecipient());
            }
            else {
                friends.add(request.getSender());
            }
        }



        return friends;
    }
    
}
