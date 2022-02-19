package com.example.adproject.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.adproject.helper.CommentHelper;
import com.example.adproject.model.Comment;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.CommentRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentAPI {

    @Autowired
    private CommentService cService;

    @Autowired
    private MealEntryRepo mRepo;

    @Autowired
    private UserRepo uRepo;

    @Autowired
    private CommentRepo cRepo;

    @GetMapping("/get")
    public ArrayList<CommentHelper> getComments(@RequestParam Integer mealEntryId) {
        List<Comment> comments = cService.findCommentByMealEntryId(mealEntryId);

        if(comments == null) {
            return null;
        }
        // Reverse order of comments so that it shows most recent on top 
        Collections.reverse(comments);

        ArrayList<CommentHelper> commentHelpers = new ArrayList<CommentHelper>();

        for(Comment comment : comments) {
            commentHelpers.add(new CommentHelper(comment));
        }

        return commentHelpers;

    }
    @PostMapping("/submit")
    public CommentHelper submitComment(@RequestParam Integer mealEntryId,
                                @RequestParam Integer activeUserId,
                                @RequestParam String caption) {

            MealEntry mealEntry = mRepo.findById(mealEntryId).get();
            User activeUser = uRepo.findById(activeUserId).get();
            
            if(mealEntry == null || activeUser == null) {
                return null;
            }

            Comment comment = new Comment(caption,activeUser,mealEntry);

            cRepo.saveAndFlush(comment);



            return new CommentHelper(comment);
            
            
        
    }


    
}
