package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.Goal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoalRepo extends JpaRepository<Goal, Integer> {

    @Query("SELECT g.targetCount FROM Goal g WHERE g.id = :id")
    int findGoalTargetCount(@Param("id") int id);

}
