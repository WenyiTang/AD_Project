package com.example.adproject.api;

import java.util.ArrayList;
import java.util.List;

import com.example.adproject.helper.BlogEntry;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.MealEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blogentry")
public class BlogEntryAPI {

    @Autowired
    private MealEntryService mService;

    @Autowired
    private UserRepo uRepo;

    @GetMapping("/page")
    public List<BlogEntry> getFeedPage(@RequestParam Integer activeUserId,
                                   @RequestParam Integer pageNo,
                                   @RequestParam Integer pageLength ) {




        List<MealEntry> mealEntries = mService.getMealEntryForFeedByPage(activeUserId, pageNo, pageLength);
        
        User activeUser = uRepo.findById(activeUserId).get();

        if(activeUser == null) {
            return null;
        }

        ArrayList<BlogEntry> blogEntries = new ArrayList<BlogEntry>();
        

        for(MealEntry mealEntry : mealEntries) {
            boolean likedByActiveUser = mService.hasUserLikedThis(activeUserId, mealEntry.getId());
            boolean flaggedByActiveUser = mService.hasUserFlaggedThis(activeUserId, mealEntry.getId());
            int numberOfLikes = mService.getTotalNumberOfLikesById(mealEntry.getId());
            blogEntries.add(new BlogEntry(mealEntry, likedByActiveUser, flaggedByActiveUser, numberOfLikes));
        }
        return blogEntries;
    }

    
    
}
