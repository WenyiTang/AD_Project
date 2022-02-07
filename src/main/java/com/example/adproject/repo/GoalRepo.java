package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.Goal;

public interface GoalRepo extends JpaRepository<Goal, Integer> {



}
