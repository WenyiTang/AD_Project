package com.example.adproject.helper;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@Getter
@Setter
public class PastMeal {
    private Integer id;
    private String imageURL;
    private String filename;
    private boolean visibility;
    private String title;
    private String description;
    private int trackScore;
    private LocalDateTime timeStamp;


    public PastMeal() {
    }

    public PastMeal(Integer id, String imageURL, String filename, boolean visibility, String title, String description, int trackScore, LocalDateTime timeStamp) {
        this.id = id;
        this.imageURL = imageURL;
        this.filename = filename;
        this.visibility = visibility;
        this.title = title;
        this.description = description;
        this.trackScore = trackScore;
        this.timeStamp = timeStamp;
    }
}
