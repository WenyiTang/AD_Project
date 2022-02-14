package com.example.adproject.repo;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MealEntryUserGoalTest {

    @Autowired
    private GoalRepo grepo;

    @Autowired
    private MealEntryRepo merepo;

    @Autowired
    private UserRepo urepo;

    @Test
    @Order(1)
    public void testFindGoalTargetCount() {
        Goal newGoal = new Goal();
        newGoal.setTargetCount(10);
        newGoal.setGoalDescription("test goal");
        grepo.saveAndFlush(newGoal);
        int targetCount = grepo.findGoalIdTargetCount(newGoal.getId());
        System.out.println("Test 1 targetCount should be 10");
        System.out.println(targetCount);
    }

    @Test
    @Order(2)
    public void testFindUserMealEntryTrackScoreWithNewObjects() {
        Goal newGoal = new Goal();
        newGoal.setTargetCount(20);
        newGoal.setGoalDescription("test goal2");
        //Do not uncomment next 2 lines - some error with PersistentObjectException: detached entity...
        //grepo.saveAndFlush(newGoal);
        //Goal currentGoal = grepo.findById(newGoal.getId()).get();
        List<Goal> goalList = new ArrayList<>();
        goalList.add(newGoal);

        User newUser = new User();
        newUser.setName("tester");
        newUser.setGoals(goalList);
        urepo.saveAndFlush(newUser);

        MealEntry mealEntry1 = new MealEntry();
        mealEntry1.setTrackScore(1);
        mealEntry1.setAuthor(newUser);
        mealEntry1.setGoal(newGoal);
        MealEntry mealEntry2 = new MealEntry();
        mealEntry2.setTrackScore(1);
        mealEntry2.setAuthor(newUser);
        mealEntry2.setGoal(newGoal);
        MealEntry mealEntry3 = new MealEntry();
        mealEntry3.setTrackScore(1);
        mealEntry3.setAuthor(newUser);
        mealEntry3.setGoal(newGoal);
        merepo.saveAndFlush(mealEntry1);
        merepo.saveAndFlush(mealEntry2);
        merepo.saveAndFlush(mealEntry3);

        int targetCount = grepo.findGoalIdTargetCount(newGoal.getId());
        List<Integer> trackScore = merepo.findUserMealEntryTrackScore(newUser.getId());

        System.out.println("Test 2 targetCount should be 20, and trackScore is [1, 1, 1]");
        System.out.println(targetCount);
        System.out.println(trackScore);

    }

    @Test
    @Order(3)
    public void testFindUserMealEntryTrackScoreWithExistingObjects() {
        Goal newGoal = grepo.findById(15).get();
        List<Goal> goalList = new ArrayList<>();
        goalList.add(newGoal);
        User newUser = urepo.findById(12).get();
        newUser.setGoals(goalList);

        MealEntry mealEntry1 = new MealEntry();
        mealEntry1.setTrackScore(1);
        mealEntry1.setAuthor(newUser);
        mealEntry1.setGoal(newGoal);
        MealEntry mealEntry2 = new MealEntry();
        mealEntry2.setTrackScore(1);
        mealEntry2.setAuthor(newUser);
        mealEntry2.setGoal(newGoal);
        MealEntry mealEntry3 = new MealEntry();
        mealEntry3.setTrackScore(1);
        mealEntry3.setAuthor(newUser);
        mealEntry3.setGoal(newGoal);
        merepo.saveAndFlush(mealEntry1);
        merepo.saveAndFlush(mealEntry2);
        merepo.saveAndFlush(mealEntry3);

        int targetCount = grepo.findGoalIdTargetCount(newGoal.getId());
        List<Integer> trackScore = merepo.findUserMealEntryTrackScore(newUser.getId());

        System.out.println(targetCount);
        System.out.println(trackScore);

    }

}
