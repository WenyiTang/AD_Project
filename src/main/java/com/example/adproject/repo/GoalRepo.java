package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;

public interface GoalRepo extends JpaRepository<Goal, Integer> {
	
	@Query("Select g from Goal g where g.author.id = :userid AND g.status ='COMPLETED'")
	public List<Goal> findCompletedGoals(@Param("userid") Integer userId);
	
	//figuring out how to query by joining tables, not sure of the table names
	//@Query("Select g from Goal g left join userGoals ug on g.id = ug.goals.id where ug.user.id = :userid AND g.status ='IN_PROGRESS'")
	@Query("Select g from Goal g where g.author.id = :userid AND g.status ='IN_PROGRESS'")
	public Goal findCurrentGoal(@Param("userid") Integer userId);

	@Query("Select g from Goal g where g.author.id = :userid AND g.status ='COMPLETED' OR g.status ='CANCELLED' ")
	public List<Goal> findPastGoals(@Param("userid") Integer userId);
}
