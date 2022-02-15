package com.example.adproject.api;

import java.util.ArrayList;
import java.util.List;

import com.example.adproject.helper.CommentHelper;
import com.example.adproject.model.Comment;
import com.example.adproject.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentAPI {

    @Autowired
    private CommentService cService;

    @GetMapping("/get")
    public ArrayList<CommentHelper> getComments(@RequestParam Integer mealEntryId) {
        List<Comment> comments = cService.findCommentByMealEntryId(mealEntryId);

        if(comments == null) {
            return null;
        }

        ArrayList<CommentHelper> commentHelpers = new ArrayList<CommentHelper>();

        for(Comment comment : comments) {
            commentHelpers.add(new CommentHelper(comment));
        }

        return commentHelpers;

    }


    
}
