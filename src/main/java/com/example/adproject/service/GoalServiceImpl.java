package com.example.adproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Goal;
import com.example.adproject.repo.GoalRepo;


@Service
public class GoalServiceImpl implements GoalService {

	@Autowired
	GoalRepo grepo;
	
	@Transactional
	public void cancelGoal(Goal goal) {
		goal.setStatus(StatusEnum.CANCELLED);
		grepo.saveAndFlush(goal);
		return;
	}
	
	@Transactional
	public void completeGoal(Goal goal) {
		goal.setStatus(StatusEnum.COMPLETED);
		grepo.saveAndFlush(goal);
		return;
	}
}
