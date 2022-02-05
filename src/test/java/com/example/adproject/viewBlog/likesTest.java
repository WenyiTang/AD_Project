package com.example.adproject.viewBlog;

import static org.junit.jupiter.api.Assertions.fail;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;

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
    


    //Run this after dropping database and running FriendRequestTest and ViewBlog
    @Test
    @Order(1)
    void testLikeEntry() {
        MealEntry mealEntry = mRepo.findById(1).get();
        User user = uRepo.findByUsername("chandler");
        mealEntry.getLikers().add(user);

        mRepo.saveAndFlush(mealEntry);
        
    }

    @Test
    @Order(2)
    void testUnlikeEntry() {
        MealEntry mealEntry = mRepo.findById(1).get();
        User user = uRepo.findByUsername("chandler");

        if(!mealEntry.getLikers().remove(user)) {
            fail();
        }
        mRepo.saveAndFlush(mealEntry);
     
     

      
        

    }
}
