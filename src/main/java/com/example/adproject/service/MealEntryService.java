package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;

import org.springframework.stereotype.Component;

@Component
public interface MealEntryService {

    public void likeEntryByObject(User user, MealEntry mealEntry);

    public void likeEntryById(Integer userId, Integer mealEntryId);

    public void unlikeEntryByObject(User user, MealEntry mealEntry);

    public void unlikeEntryById(Integer userId, Integer mealEntryId);

    public List<User> getLikersById(Integer mealEntryId);

    public void removeAllLikesById(Integer mealEntryId);

    public Integer getTotalNumberOfLikesById(Integer mealEntryId);

    public Boolean hasUserLikedThis(Integer userId, Integer mealEntryId);


    
}
