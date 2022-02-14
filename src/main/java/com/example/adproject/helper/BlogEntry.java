package com.example.adproject.helper;

import java.time.LocalDateTime;

import com.example.adproject.model.MealEntry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogEntry{
    private Integer id;
	private String imageURL;

	private boolean visibility;
	private String title;
	private String description;
    private LocalDateTime timeStamp;

    private boolean likedByActiveUser;
    private boolean flaggedByActiveUser;
    private int numberOfLikes;
    private String authorUsername;
    private Integer authorId;

    public BlogEntry(MealEntry mealEntry,
                            boolean likedByActiveUser, 
                            boolean flaggedByActiveUser, 
                            int numberOfLikes) {

        this.id = mealEntry.getId();
        this.imageURL = mealEntry.getImageURL();
        this.visibility = mealEntry.isVisibility();
        this.title = mealEntry.getTitle();
        this.description = mealEntry.getDescription();
        this.timeStamp = mealEntry.getTimeStamp();

        this.likedByActiveUser = likedByActiveUser;
        this.numberOfLikes = numberOfLikes;
        this.flaggedByActiveUser = flaggedByActiveUser;
        this.authorUsername = mealEntry.getAuthor().getUsername();
        this.authorId = mealEntry.getAuthor().getId();
    }



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", imageURL='" + getImageURL() + "'" +
            ", visibility='" + isVisibility() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", likedByActiveUser='" + isLikedByActiveUser() + "'" +
            ", flaggedByActiveUser='" + isFlaggedByActiveUser() + "'" +
            ", numberOfLikes='" + getNumberOfLikes() + "'" +
            "}";
    }
    
    
}
