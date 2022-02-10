package com.example.adproject.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.adproject.model.Goal;


public interface GoalService {

	public void completeGoal(Goal goal);
	
	public void cancelGoal(Goal goal);
}
