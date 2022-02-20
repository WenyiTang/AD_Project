package com.example.adproject.api;

import java.util.ArrayList;
import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.MealEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/likes")
public class LikesAPI {
    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    MealEntryService mService;

    @GetMapping("/total")
    public Integer getTotalNumberOfLikes(@RequestParam Integer mealEntryId) {

        return mService.getTotalNumberOfLikesById(mealEntryId);
        
    }

    @PostMapping("/like")
    public void likeMealEntry(@RequestParam Integer userId, @RequestParam Integer mealEntryId) {
        
        mService.likeEntryById(userId, mealEntryId);

    }

    @PostMapping("/unlike")
    public void unlikeMealEntry(@RequestParam Integer userId, @RequestParam Integer mealEntryId) {
        
        mService.unlikeEntryById(userId, mealEntryId);

    }
    

    @GetMapping("/get")
    public ArrayList<String> getLikes(@RequestParam Integer mealEntryId) {

        MealEntry mealEntry = mRepo.findById(mealEntryId).get();
        if(mealEntry == null) {
            return null;
        }
        List<User> likers = mealEntry.getLikers();
        ArrayList<String> likerUsernames = new ArrayList<>();

        for(User liker : likers) {
            likerUsernames.add(liker.getUsername());
        }

        return likerUsernames;

        
    }
    
}
