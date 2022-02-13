package com.example.adproject.api;

import com.example.adproject.model.Goal;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class profileAPI {



    @Autowired
    GoalRepo gRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    MealEntryRepo mRepo;
    
    
	@RequestMapping(value = "/currentgoal",method = RequestMethod.POST)
    public Goal viewcurrentgoal(@RequestParam String UserName){

			User user =uRepo.findByUsername(UserName);
        	Goal currentgoal = gRepo.findCurrentGoal(user.getId());
//        	String currentGoal1 =currentgoal.getGoalDescription();
//        	return currentGoal1;
        	return currentgoal;
        
    }
	
	@RequestMapping(value = "/userProfile",method = RequestMethod.POST)
    public User viewuserProfile(@RequestParam String UserName){

			User user =uRepo.findByUsername(UserName);
        	
//        	String currentGoal1 =currentgoal.getGoalDescription();
//        	return currentGoal1;
        	return user;
        
    }
	
	@RequestMapping(value = "/goalsMeal",method = RequestMethod.POST)
	public List<String> goalsMeal(@RequestParam String UserName) {
		
		List<String> strList = new ArrayList<String>();
		User user =uRepo.findByUsername(UserName);
		int userid = user.getId();
		long countOnt = mRepo.findEntryByAuthor(userid).stream()
				.filter(x->x.getTrackScore()==1)
				.count();
		String countOnT = String.valueOf(countOnt);
		long countOfft = mRepo.findEntryByAuthor(userid).stream()
				.filter(x->x.getTrackScore() == 0)
				.count();
		String countOffT = String.valueOf(countOfft);
		long totalmeals = countOnt + countOfft;
		double percentCount = (countOnt*100/totalmeals) ;
		String percentCount1 = String.format("%.1f",percentCount);
		strList.add(countOnT);
		strList.add(countOffT);
		strList.add(percentCount1);
		return strList;
	}
	
}

