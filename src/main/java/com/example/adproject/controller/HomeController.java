package com.example.adproject.controller;

import java.security.Principal;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adproject.model.Goal;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.service.MealEntryService;
import com.example.adproject.service.UserService;


@RestController
@RequestMapping("/api/dashboard")
public class HomeController {
	
	float[] mealTrackList = new float[2];
	float[] feelingList = new float[4];
	
	@Autowired
	private GoalRepo gRepo;
	
	@Autowired
	private MealEntryRepo mRepo;
	
	@Autowired
	private UserService uService;

    @GetMapping("/getStringData")
    public String getString(){
        return "{'msg':'AnyData','success':200'}";
    }

   
    @GetMapping("/getCurrentGoal/{userId}")
    public String currentGoal(@PathVariable Integer userId) {
    	if (gRepo.findCurrentGoal(userId)==null) {
    		return "no goal set yet";
    	}
    	else {
    		return gRepo.findCurrentGoal(userId).getGoalDescription();
    	}
    }
    	
  
  @GetMapping("/getTrack/{userId}")
  public ResultJson getMealTrack(@PathVariable Integer userId) {
  	//Integer userId = 3;
		float countOnT = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getTrackScore()==1)
				.count();
		float countOffT= mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getTrackScore() == 0)
				.count();

		ResultJson r = new ResultJson();
		mealTrackList[0] = countOnT;
		mealTrackList[1] = countOffT;
		r.setMealTrack(mealTrackList);
		return r;
  }
    
  @GetMapping("/getFeeling/{userId}")
  public ResultJson getFeeling(@PathVariable Integer userId) {
	  float countJoy = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "JOY")
				.count();
		float countHappy = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "HAPPY")
				.count();
		float countPensive = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "PENSIVE")
				.count();
		float countCry = mRepo.findIPEntryByAuthor(userId).stream()
				.filter(x->x.getFeeling().toString()== "CRY")
				.count();

		ResultJson r = new ResultJson();
		feelingList[0] = countJoy;
		feelingList[1] = countHappy;
		feelingList[2] = countPensive;
		feelingList[3] = countCry;
		
		r.setFeeling(feelingList);
		return r;
  }
    


    
}
