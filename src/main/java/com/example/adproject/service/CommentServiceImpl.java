package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.Comment;
import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.CommentRepo;
import com.example.adproject.repo.MealEntryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CommentServiceImpl implements CommentService{
    @Autowired
    private MealEntryRepo mRepo;

    @Autowired
    private CommentRepo cRepo;

    @Override
    public List<Comment> findCommentByMealEntryId(Integer mealEntryId) {
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();
        if(mealEntry == null) {
            return null;
        }


        return cRepo.findCommentByMealEntry(mealEntry);
    }
    
}
