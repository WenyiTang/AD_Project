package com.example.adproject.api;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.service.MealEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
public class SocialFeedAPI {

    @Autowired
    private MealEntryService mService;

    @GetMapping("/page")
    public List<MealEntry> getPage(@RequestParam Integer userId,
                                   @RequestParam Integer pageNo,
                                   @RequestParam Integer pageLength ) {




        return mService.getMealEntryForFeedByPage(userId, pageNo, pageLength);
    }

    
    
}
