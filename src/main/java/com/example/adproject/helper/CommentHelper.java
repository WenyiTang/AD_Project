package com.example.adproject.helper;

import com.example.adproject.model.Comment;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CommentHelper {
  
    public CommentHelper(Comment comment) {
        this.commentId = comment.getId();
        this.caption = comment.getCaption();
        this.mealEntryId = comment.getMealEntry().getId();
        this.authorUsername = comment.getAuthor().getUsername();
        this.authorId = comment.getAuthor().getId();


    }
    private Integer commentId;
	private String caption;
    private Integer mealEntryId;
    private String authorUsername;
    private Integer authorId;
}
