package com.example.adproject.service;

import com.example.adproject.model.FriendRequest;
import com.example.adproject.model.User;

import org.springframework.stereotype.Component;

@Component
public interface FriendRequestService {
    
    public boolean sendRequest(User sender, User recipient);

    public void acceptRequest(Integer id);

    public void rejectRequest(FriendRequest request);

    public FriendRequest findAcceptedRequestsByUsers(User firstUser, User secondUser);

    public void deleteRequest(FriendRequest request);
}
