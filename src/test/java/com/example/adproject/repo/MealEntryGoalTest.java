package com.example.adproject.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.helper.FeelingEnum;
import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Comment;
import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MealEntryGoalTest {

	@Autowired
	private GoalRepo grepo;
	
	@Autowired
	private MealEntryRepo merepo;
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private CommentRepo crepo;
	
	@Test
	@Order(1)
	public void createGoals() {
		Goal goal1 = new Goal();
		goal1.setGoalDescription("Eat very healthy");
		goal1.setTotalMealCount(12);
		goal1.setTargetCount(3);
		goal1.setStatus(StatusEnum.STARTED);
		goal1.setStartDate(LocalDate.now());
		goal1.setEndDate(LocalDate.now().plusDays(2));
		grepo.save(goal1);
		
		Goal goal2 = new Goal();
		goal2.setGoalDescription("Eat healthy");
		goal2.setTotalMealCount(20);
		goal2.setTargetCount(4);
		goal2.setStatus(StatusEnum.IN_PROGRESS);
		goal2.setStartDate(LocalDate.now());
		goal2.setEndDate(LocalDate.now().plusDays(5));
		grepo.save(goal2);
	}
	
	@Test
	@Order(2)
	public void createMealEntries() {
		Goal goal1 = grepo.findById(1).get();
		Goal goal2 = grepo.findById(2).get();
		User newUser = new User();
		urepo.save(newUser);
		
		MealEntry mealEntry1 = new MealEntry();
		mealEntry1.setImageURL("www.someurl.sg");
		mealEntry1.setVisibility(false);
		mealEntry1.setTitle("First Meal");
		mealEntry1.setDescription("2 Tacos and Mexican cola");
		mealEntry1.setFlagged(true);
		mealEntry1.setFeeling(FeelingEnum.JOYFUL);
		mealEntry1.setTrackScore(0);
		mealEntry1.setTimeStamp(LocalDateTime.now());
		mealEntry1.setGoal(goal1);
		//mealEntry1.setAuthor(newUser);
		merepo.save(mealEntry1);
	}
	
	@Test
	@Order(3)
	public void createMealEntryLikersAndComments() {
		User userA = new User();
		User userB = new User();
		User userC = new User();
		urepo.save(userA);
		urepo.save(userB);
		urepo.save(userC);
		List<User> likers = new ArrayList<>();
		likers.add(userA);
		likers.add(userB);
		likers.add(userC);
		
		Comment comment1 = new Comment();
		Comment comment2 = new Comment();
		crepo.save(comment1);
		crepo.save(comment2);
		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);
		
		MealEntry mealEntry1 = merepo.findById(1).get();
		mealEntry1.setLikers(likers);
		mealEntry1.setComments(comments);
		merepo.save(mealEntry1);
	}
	
	
}
