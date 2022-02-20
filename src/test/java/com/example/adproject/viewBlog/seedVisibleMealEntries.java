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
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.UserService;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
public class seedVisibleMealEntries {
    @Autowired 
    UserRepo uRepo;

    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    MealEntryService mService;

    @Autowired
    UserService uService;

    private String mealImageDir = "upload/";
    private String[] mealImageFilenames = {"salad.png", "shakeshack.jpeg","kookeemian.jpeg","wantan_mee.jpeg","banmian.jpeg"};
    private String[] titles = {"Salad", "Shake Shack", "Noodles", "Wonton noodles", "Banmian"};
    private String lorem = "Lorem ipsum dolor sit amet consectetur " 
                            + "adipisicing elit. Aspernatur excepturi quod voluptatum "
                            + "repudiandae recusandae. Ea eos voluptatem pariatur " 
                            + "numquam distinctio atque dolorum labore, ab incidunt ";

    private LocalDateTime today = LocalDateTime.now();
    private int trackScore = 1;

    private String[] captions = {"Hi everyone!","Wow! looks great.", "So yummy!", "noice! where was this?", "did you make it yourself?", "nice photography skills"};

    @Test
    void testFindJillsFriends() {
        String username = "jill";
        User user = uRepo.findByUsername(username);
        if(user == null) {
            fail();
        }
        List<User> friends = uService.findFriendsOf(user);

        assertEquals(8,friends.size());

    }

    @Test
    void seedMealEntries() {
        List<User> users = uRepo.findAll();

        // int uniqueEntries = titles.length - 1;
        ArrayList<MealEntry> entriesToInsert = new ArrayList<MealEntry>();

        for(User author : users) {
          
            for(int i = 0; i < titles.length * 2; i++) {
                String filename = mealImageDir + mealImageFilenames[i % titles.length];
                boolean visibility = true;
                String title = titles[i % titles.length];
                String description = lorem;
                boolean flagged = false;
                FeelingEnum feeling = FeelingEnum.JOY;
                int trackScore = this.trackScore;
                LocalDateTime timeStamp = today.minusDays(i);
                entriesToInsert.add(new MealEntry( visibility, title, description, flagged, feeling, trackScore, timeStamp, author, filename));


            }
        }
        mRepo.saveAll(entriesToInsert);

    }


}
