package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.Goal;
import com.example.adproject.model.User;

public interface GoalRepo extends JpaRepository<Goal, Integer> {
	


}
