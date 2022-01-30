package com.example.adproject.repo;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.adproject.AdProjectApplication;
import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Goal;
import com.example.adproject.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AdProjectApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntryCommentLikeTest {

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
	public void createUsers() {
		User u1 = new User();
		u1.setName("Gary");
		urepo.save(u1);
		
		User u2 = new User();
		u2.setName("Squidward");
		urepo.save(u2);
	}
	
	@Test
	@Order(2)
	public void createGoal() {
		User u1 = urepo.findById(1).get();
		Goal goal1 = new Goal();
		goal1.setGoalDescription("Eat very healthy");
		goal1.setTotalMealCount(12);
		goal1.setTargetCount(3);
		goal1.setStatus(StatusEnum.STARTED);
		goal1.setStartDate(LocalDate.now());
		goal1.setEndDate(LocalDate.now().plusDays(2));
		u1.getGoals().add(goal1);
		urepo.save(u1);
		
		User u2 = urepo.findById(2).get();
		Goal goal2 = new Goal();
		goal2.setGoalDescription("Eat healthy");
		goal2.setTotalMealCount(20);
		goal2.setTargetCount(4);
		goal2.setStatus(StatusEnum.IN_PROGRESS);
		goal2.setStartDate(LocalDate.now());
		goal2.setEndDate(LocalDate.now().plusDays(5));
		u2.getGoals().add(goal2);
		
		Goal goal3 = new Goal();
		goal3.setGoalDescription("No sweet drinks");
		goal3.setTotalMealCount(30);
		goal3.setTargetCount(20);
		goal3.setStatus(StatusEnum.STARTED);
		goal3.setStartDate(LocalDate.now());
		goal3.setEndDate(LocalDate.now().plusDays(5));
		u2.getGoals().add(goal3);
		urepo.save(u2);
	}
	
	@Test
	@Order(3)
	public void findGoalsByUser() {
		User u1 = urepo.findById(1).get();
		List<Goal> u1Goals = u1.getGoals();
		System.out.println(u1Goals.size());
		for (Goal g : u1Goals) {
			System.out.println(g.getGoalDescription());
		}
		
		User u2 = urepo.findById(2).get();
		List<Goal> u2Goals = u2.getGoals();
		System.out.println(u2Goals.size());
		for (Goal g : u2Goals) {
			System.out.println(g.getGoalDescription());
		}
	}
	
}
