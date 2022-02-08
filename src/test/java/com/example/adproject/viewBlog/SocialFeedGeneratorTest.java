package com.example.adproject.viewBlog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.MealEntryService;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SocialFeedGeneratorTest {
    
    @Autowired
    MealEntryService mService;

    @Autowired
    MealEntryRepo mRepo;

    @Test
    @Order(1)
    void testGetEntriesForFeed() {
        Integer userId = 5; //chandler, because he's friends with everyone LOL

        List<MealEntry> mealEntries = mService.getAllVisibleFriendEntries(userId);

        List<MealEntry> chandlerEntries = mService.getVisibleMealEntryByUserId(userId);

        List<MealEntry> visibleEntries = mRepo.findAllVisibleMealEntries();

        assertEquals(visibleEntries.size()-chandlerEntries.size(),mealEntries.size());
         
    }
    
    @Test
    @Order(2)
    void testGetFeedEntriesByPage() {
        Integer userId = 5; 
        Integer pageNo = 0;
        Integer pageLength = 10; // number of entries per page
        List<MealEntry> mealEntries = mService.getMealEntryForFeedByPage(userId, pageNo, pageLength);

        for(MealEntry mealEntry : mealEntries) {
            System.out.println(mealEntry);
        }

        

    }
    
}
