package com.example.adproject.viewBlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.helper.FeelingEnum;
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
public class SocialFeedTest {

    @Autowired
    private UserRepo uRepo;

    @Autowired
    private MealEntryRepo mRepo;
    
    private String mealImageDir = "/blog/images/";
    private String[] mealImageFilenames = {"salad.png", "shakeshack.jpeg","kookeemian.jpeg","wantan_mee.jpeg","banmian.jpeg"};
    private String[] titles = {"Salad", "Shake Shack", "Noodles", "Wonton noodles", "Banmian"};
    private String lorem = "Lorem ipsum dolor sit amet consectetur " 
                            + "adipisicing elit. Aspernatur excepturi quod voluptatum "
                            + "repudiandae recusandae. Ea eos voluptatem pariatur " 
                            + "numquam distinctio atque dolorum labore, ab incidunt ";

    private LocalDateTime today = LocalDateTime.now();
    private int trackScore = 2;
    private String[] usernames = {"monica","phoebe","rachel","ross","chandler","joey"};
    private ArrayList<User> users = new ArrayList<User>();

    @Test
    @Order(1)
    void testInsertTenVisibleEntriesPerUser() {
        mRepo.deleteAll();
        for(String username : usernames) {
            User user = uRepo.findByUsername(username);
            if(user == null) {
                fail();
            }
            users.add(user);
        }

        // int uniqueEntries = titles.length - 1;
        ArrayList<MealEntry> entriesToInsert = new ArrayList<MealEntry>();

        for(User author : users) {
          
            for(int i = 0; i < titles.length * 2; i++) {
                String imageURL = mealImageDir + mealImageFilenames[i % titles.length];
                boolean visibility = true;
                String title = titles[i % titles.length];
                String description = lorem;
                boolean flagged = false;
                FeelingEnum feeling = FeelingEnum.JOYFUL;
                int trackScore = this.trackScore;
                LocalDateTime timeStamp = today.minusDays(i);
                entriesToInsert.add(new MealEntry(imageURL, visibility, title, description, flagged, feeling, trackScore, timeStamp, author));


            }
        }
        mRepo.saveAll(entriesToInsert);

    }

    @Test
    void testDeleteAllMealEntries() {
        mRepo.deleteAll();
        List<MealEntry> entries = mRepo.findAll();
        assertEquals(0, entries.size());
        
    }
    
}
