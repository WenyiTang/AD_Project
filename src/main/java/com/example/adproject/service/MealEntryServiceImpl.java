package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MealEntryServiceImpl implements MealEntryService{
    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Override
    public void likeEntryByObject(User user, MealEntry mealEntry) {
        if(user == null || mealEntry == null) {
            return;
        }

        mealEntry.getLikers().add(user);

        mRepo.saveAndFlush(mealEntry);
    }

    
    @Override
    public void likeEntryById(Integer userId, Integer mealEntryId) {
        User user = uRepo.findById(userId).get();
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if(user == null || mealEntry == null) {
            return;
        }
      
        likeEntryByObject(user, mealEntry);

        
    }

    @Override
    public void unlikeEntryByObject(User user, MealEntry mealEntry) {
        if(user == null || mealEntry == null) {
            return;
        }

        mealEntry.getLikers().remove(user);

        mRepo.saveAndFlush(mealEntry);
        
        
    }

    @Override
    public void unlikeEntryById(Integer userId, Integer mealEntryId) {
        User user = uRepo.findById(userId).get();
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if(user == null || mealEntry == null) {
            return;
        }
        unlikeEntryByObject(user, mealEntry);
        
    }


    @Override
    public List<User> getLikersById(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if (mealEntry == null) {
            return null;
        }

        return mealEntry.getLikers();

        

        
    }


    @Override
    public void removeAllLikesById(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if (mealEntry == null) {
            return;
        }

        mealEntry.getLikers().clear();
        mRepo.saveAndFlush(mealEntry);
        
    }


    @Override
    public Integer getTotalNumberOfLikesById(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if (mealEntry == null) {
            return -1;
        }

       
        return mealEntry.getLikers().size();
    }


    @Override
    public Boolean hasUserLikedThis(Integer userId, Integer mealEntryId) {
        List<User> likers = getLikersById(mealEntryId);

        for(User liker : likers) {
            if(liker.getId() == userId){
                return true;
            }
        }

        return false;
    }


    
    
}
