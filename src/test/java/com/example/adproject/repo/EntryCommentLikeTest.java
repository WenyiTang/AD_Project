package com.example.adproject.repo;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
		goal1.setStatus(StatusEnum.IN_PROGRESS);
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
		goal3.setStatus(StatusEnum.IN_PROGRESS);
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
	
	@Test
	@Order(4)
	public void addEntry() {
		User u1 = urepo.findById(1).get();
		Goal g1 = grepo.findById(1).get();
		MealEntry mealEntry1 = new MealEntry();
		mealEntry1.setImageURL("www.someurl.sg");
		mealEntry1.setVisibility(false);
		mealEntry1.setTitle("First Meal");
		mealEntry1.setDescription("2 Tacos and Mexican cola");
		mealEntry1.setFlagged(true);
		mealEntry1.setFeeling(FeelingEnum.JOY);
		mealEntry1.setTrackScore(0);
		mealEntry1.setTimeStamp(LocalDateTime.now());
		mealEntry1.setGoal(g1);
		mealEntry1.setAuthor(u1);
		merepo.save(mealEntry1);

		MealEntry mealEntry2 = new MealEntry();
		mealEntry2.setImageURL("www.222.sg");
		mealEntry2.setVisibility(false);
		mealEntry2.setTitle("Second Meal");
		mealEntry2.setDescription("McDonalds");
		mealEntry2.setFlagged(true);
		mealEntry2.setFeeling(FeelingEnum.JOY);
		mealEntry2.setTrackScore(0);
		mealEntry2.setTimeStamp(LocalDateTime.now());
		mealEntry2.setGoal(g1);
		mealEntry2.setAuthor(u1);
		merepo.save(mealEntry2);
	}
	
	@Test
	@Order(5)
	public void testEntry() {
		User u1 = urepo.findById(1).get();
		List<MealEntry> entries = u1.getEntries();
		System.out.println(entries.size());
		for (MealEntry m : entries) {
			System.out.println(m.getTitle());
		}
	}
	
	
	@Test
	@Order(6)
	public void deleteGoalFromMealEntry() {
		
		List<MealEntry> ml = merepo.findAll();
		System.out.println(ml.size());
		for (MealEntry m : ml) {
			if (m.getGoal() != null) {
				if (m.getGoal().getId() == 1) {
					System.out.println("same");
					m.setGoal(null);
					merepo.save(m);
				}
			}
		}
	}
	
	@Test
	@Order(7)
	public void deleteGoal() {
		//done via user. 
		//MealEntry.goal that is set to particular goal must be changed to null beforehand
		User u2 = urepo.findById(1).get();
		Goal g = grepo.findById(1).get();
		u2.getGoals().remove(g);
		urepo.save(u2);
	}
	
	@Test
	@Order(8)
	public void testComment() {
		User u = urepo.findById(1).get();
		MealEntry mealEntry1 = merepo.findById(1).get();
		Comment c1 = new Comment();
		c1.setCaption("testwy");
		c1.setMealEntry(mealEntry1);
		c1.setAuthor(u);
		crepo.save(c1);
		
		Comment c2 = new Comment();
		c2.setCaption("testwy2");
		c2.setMealEntry(mealEntry1);
		c2.setAuthor(u);
		crepo.save(c2);
	}
	
	@Test
	@Order(9)
	public void getCommentsFromUser() {
		User u = urepo.findById(1).get();
		List<Comment> comments = u.getComments();
		for (Comment c : comments) {
			System.out.println(c.getCaption());
		}
	}
	
	@Test
	@Order(10)
	public void getCommentsFromMealEntry() {
		MealEntry mealEntry1 = merepo.findById(1).get();
		List<Comment> comments = mealEntry1.getComments();
		for (Comment c : comments) {
			System.out.println(c.getCaption());
		}
	}
	
	@Test
	@Order(11)
	public void likeEntry() {
		MealEntry m1 = merepo.findById(1).get();
		User u1 = urepo.findById(1).get();
		m1.getLikers().add(u1);
		merepo.save(m1);
	}
	
	@Test
	@Order(11)
	public void likeEntry2() {
		MealEntry m1 = merepo.findById(2).get();
		User u2 = urepo.findById(1).get();
		m1.getLikers().add(u2);
		merepo.save(m1);
	}
	
	@Test
	@Order(12)
	public void testLike() {
		//by user
		User u1 = urepo.findById(1).get();
		List<MealEntry> ml = u1.getLikedEntries();
		for (MealEntry m : ml) {
			System.out.println(m.getId());
		}
		
		//by meal entry
		MealEntry m1 = merepo.findById(1).get();
		List<User> likers = m1.getLikers();
		for (User l : likers) {
			System.out.println(l.getName());
		}
	}
	
	@Test
	@Order(13)
	public void unlike() {
		MealEntry m1 = merepo.findById(1).get();
		User u1 = urepo.findById(1).get();
		System.out.println(m1.getLikers().size());
		List<User> likers = m1.getLikers();
		for (User u : likers) {
			if (u.getId() == u1.getId()) {
				m1.getLikers().remove(u);
				break;
			}
		}
		System.out.println(m1.getLikers().size());
		merepo.save(m1);
		System.out.println(m1.getLikers().size());
	}
	
}
