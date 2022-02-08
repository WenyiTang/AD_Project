package com.example.adproject.service;

import java.util.List;

import com.example.adproject.helper.RequestEnum;
import com.example.adproject.model.FriendRequest;
import com.example.adproject.model.User;
import com.example.adproject.repo.FriendRequestRepo;
import com.example.adproject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    FriendRequestRepo fRepo;

    @Autowired
    UserRepo uRepo;

    @Override
    public boolean sendRequest(User sender, User recipient) {

        // Assume that usernames are unique
        if(sender.getUsername().equals(recipient.getUsername())){
            return false;
        }
        
        List<FriendRequest> existingRequests = fRepo.findExistingRequests(sender, recipient);
        if(existingRequests.size() > 0) {
            return false;
        }

        fRepo.saveAndFlush(new FriendRequest(sender,recipient));
        return true;
   
    }

    @Override
    public void acceptRequest(Integer id) {
        FriendRequest request = fRepo.findById(id).get();

        request.setStatus(RequestEnum.ACCEPTED);

        fRepo.saveAndFlush(request);
        
    }

    @Override
    public void rejectRequest(FriendRequest request) {

        fRepo.delete(request);
        
    }
    
}
