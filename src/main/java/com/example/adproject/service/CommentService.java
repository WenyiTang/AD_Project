package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.Comment;

import org.springframework.stereotype.Component;

@Component
public interface CommentService {

    public List<Comment> findCommentByMealEntryId(Integer mealEntryId);
    
}
