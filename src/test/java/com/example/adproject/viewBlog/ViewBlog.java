package com.example.adproject.viewBlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.helper.FeelingEnum;
import com.example.adproject.model.Comment;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.CommentRepo;
import com.example.adproject.repo.FriendRequestRepo;
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
public class ViewBlog {

    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    FriendRequestRepo fRepo;

    @Autowired
    CommentRepo cRepo;

    private String mealImageDir = "/blog/images/";
    private String[] mealImageFilenames = {"salad.png", "shakeshack.jpeg","kookeemian.jpeg","wantan_mee.jpeg","banmian.jpeg"};
    private String[] titles = {"Salad", "Shake Shack", "Noodles", "Wonton noodles", "Banmian"};
    private String lorem = "Lorem ipsum dolor sit amet consectetur " 
                            + "adipisicing elit. Aspernatur excepturi quod voluptatum "
                            + "repudiandae recusandae. Ea eos voluptatem pariatur " 
                            + "numquam distinctio atque dolorum labore, ab incidunt ";

    private LocalDateTime today = LocalDateTime.now();
    private int trackScore = 2;

    private String[] captions = {"Hi everyone!","Wow! looks great.", "So yummy!", "noice! where was this?", "did you make it yourself?", "nice photography skills"};

    //@Test
    @Order(1)
    void testInsertUsers(){
        fRepo.deleteAll();
        uRepo.deleteAll();
        ArrayList<User> users = new ArrayList<User>();
        String[] friends = {"monica","phoebe","rachel","ross","chandler","joey"};

        for (String friend : friends){
            users.add(new User(friend,friend));
        }

        uRepo.saveAll(users);

        List<User> insertedUsers = uRepo.findAll();
        assertEquals(insertedUsers.size(), users.size());
        

    }
    @Test
    @Order(2)
    void testInsertMealEntries() {
        mRepo.deleteAll();


        User author = uRepo.findByUsername("Monica");
        if(author == null) {
            fail();
        }

    
        ArrayList<MealEntry> entriesToInsert = new ArrayList<MealEntry>();
        for (int i = 0; i < titles.length; i++){
            String imageURL = mealImageDir + mealImageFilenames[i];
            boolean visibility = i % 2 == 0 ? true : false;
            String title = titles[i];
            String description = lorem;
            boolean flagged = false;
            FeelingEnum feeling = FeelingEnum.JOY;
            int trackScore = this.trackScore;
            LocalDateTime timeStamp = today.minusDays(i);
        
            

            entriesToInsert.add(new MealEntry(imageURL, visibility, title, description, flagged, feeling, trackScore, timeStamp, author));

        }
        mRepo.saveAll(entriesToInsert);

        List<MealEntry> entries = mRepo.findAll();
        assertEquals(entries.size(),entriesToInsert.size());
        
    }

    @Test
    @Order(3)
    void testFindVisibleEntriesByAuthor(){


        

        User chandler = uRepo.findByUsername("Chandler");
        if(chandler == null) {
            fail();
        }
        int i = 0;
        String imageURL = mealImageDir + mealImageFilenames[i];
        boolean visibility = true;
        String title = titles[i];
        String description = lorem;
        boolean flagged = false;
        FeelingEnum feeling = FeelingEnum.JOY;
        int trackScore = this.trackScore;
        LocalDateTime timeStamp = today.minusDays(i);
        mRepo.saveAndFlush(new MealEntry(imageURL, visibility, title, description, flagged, feeling, trackScore, timeStamp, chandler));

        User monica = uRepo.findByUsername("Monica");
        if(monica == null) {
            fail();
        }

        List<MealEntry> monicaEntries = mRepo.findVisibleMealEntryByAuthor(monica);

        assertEquals(3,monicaEntries.size());
    }

    @Test
    @Order(4)
    void testInsertComments() {
        cRepo.deleteAll();
        int id = 1;//hard coded for now
        int numberOfComments = 6;
        List<Comment> insertedComments = insertCommentsToMealEntry(id,numberOfComments);

        

        List<Comment> comments = cRepo.findAll();

        assertEquals(comments.size(), insertedComments.size());

    }

    @Test
    @Order(5)
    void testFindCommentsByMealEntry() {
        // first we insert more comments to the 3rd meal entry
        cRepo.deleteAll();
        int[] ids = {1,3};
        int[] numbersOfComments = {6,3};

        for(int i = 0 ; i < ids.length; i++) {
            insertCommentsToMealEntry(ids[i], numbersOfComments[i]);
        }

        int id = ids[0];
        int numberOfComment = numbersOfComments[0];

        MealEntry entry = mRepo.findById(id).get();
        if(entry == null) {
            fail();
        }

        List<Comment> comments = cRepo.findCommentByMealEntry(entry);

        assertEquals(numberOfComment,comments.size());

    }





    private ArrayList<Comment> insertCommentsToMealEntry(int id,int num) {
        MealEntry entry = mRepo.findById(id).get();
        if(entry == null) {
            fail();
        }
        List<User> users = uRepo.findAll();
        ArrayList<Comment> commentsToInsert = new ArrayList<Comment>();




        for(int i = 0; i < num;i++) {
            commentsToInsert.add(new Comment(captions[i],users.get(i),entry));

        }

        cRepo.saveAll(commentsToInsert);
        return commentsToInsert;

    }

   
}
