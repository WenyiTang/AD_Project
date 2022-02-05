package com.example.adproject.viewBlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
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
public class likesTest {
    @Autowired
    private MealEntryRepo mRepo;

    @Autowired
    private UserRepo uRepo;

    @Autowired
    private MealEntryService mService;
    


    //Run this after dropping database and running FriendRequestTest and ViewBlog
    @Test
    @Order(1)
    void testLikeEntryByObject() {
        Integer mealEntryId = 1;
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();
        User user = uRepo.findByUsername("chandler");

        mService.likeEntryByObject(user, mealEntry);
        MealEntry entryAfterLike = mRepo.findById(mealEntryId).get();
        List<User> likers = entryAfterLike.getLikers();
        assertEquals(likers.size(),1);
    }

    @Test
    @Order(2)
    void testUnlikeEntryByObject() {
        Integer mealEntryId = 1;
        MealEntry mealEntry = mRepo.findById(1).get();
        User user = uRepo.findByUsername("chandler");

        mService.unlikeEntryByObject(user, mealEntry);
        MealEntry entryAfterLike = mRepo.findById(mealEntryId).get();
        List<User> likers = entryAfterLike.getLikers();
        assertEquals(likers.size(),0);
        

    }

    @Test
    @Order(3)
    void testLikeEntryByIds() {
        Integer mealEntryId = 1;
        Integer userId = 5;
        mService.likeEntryById(userId,mealEntryId);
        MealEntry entryAfterLike = mRepo.findById(mealEntryId).get();
        List<User> likers = entryAfterLike.getLikers();
        assertEquals(likers.size(),1);
    }

    @Test
    @Order(4)
    void testUnikeEntryByIds() {
        Integer mealEntryId = 1;
        Integer userId = 5;
        mService.unlikeEntryById(userId,mealEntryId);
        MealEntry entryAfterLike = mRepo.findById(mealEntryId).get();
        List<User> likers = entryAfterLike.getLikers();
        assertEquals(likers.size(),0);
    }

    @Test
    @Order(5)
    void testGetLikersById() {
        Integer mealEntryId = 1;
        Integer[] userIds = {1,2,3,4,5,6};

        for(Integer userId : userIds) {
            mService.likeEntryById(userId,mealEntryId);
        }
        List<User> likers = mService.getLikersById(mealEntryId);

        assertEquals(likers.size(),userIds.length);
    }

    @Test
    @Order(7)
    void testHasUserLikedThis() {
        Integer mealEntryId = 1;
        Integer userId = 2;


        assertTrue(mService.hasUserLikedThis(userId, mealEntryId));

    }

    @Test
    @Order(8)
    void testRemoveAllLikes() {
        Integer mealEntryId = 1;
        mService.removeAllLikesById(mealEntryId);
        List<User> likers = mService.getLikersById(mealEntryId);

        assertEquals(likers.size(),0);

    }
}
