package com.example.adproject.api;

import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Goal;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.GoalService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api")
public class profileAPI {



    @Autowired
    GoalRepo gRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    MealEntryRepo mRepo;
    
    @Autowired
    GoalService gService;
    
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
	
	@RequestMapping(value = "/saveProfile",method = RequestMethod.POST)
    public String saveProfile(@RequestParam String UserName,@RequestParam String Name, @RequestParam String dateOfBirth,
                                       @RequestParam String Height, @RequestParam String Weight, @RequestParam MultipartFile multipartFile,@RequestParam String fileName) {
        System.out.println("data from client------VVVV");
        System.out.println(UserName);

        User user =uRepo.findByUsername(UserName);
        user.setName(Name);
        SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date dateofBirth = strToDate.parse(dateOfBirth);
			LocalDate localDate=dateofBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			user.setDateOfBirth(localDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
  
        String uploadDir = "./images/" + user.getId();

        Path uploadPath = Paths.get(uploadDir);

        // Saving user's profile pic into directory
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            File profileImg = new File("images/" + user.getId() + "/" + fileName);
            File copyToStatic = new File("src/main/resources/static/blog/images/" +fileName);
            profileImg.createNewFile();
            copyToStatic.createNewFile();
            FileOutputStream fout = new FileOutputStream(profileImg);
            FileOutputStream fou1 = new FileOutputStream(copyToStatic);
            fout.write(multipartFile.getBytes());
            fou1.write(multipartFile.getBytes());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.setHeight(Double.valueOf(Height));
        user.setWeight(Double.valueOf(Weight));
        user.setProfilePic(fileName);
        uRepo.saveAndFlush(user);
        return "successful";

	    }
	
	//end goal
	@RequestMapping(value = "/endGoal",method = RequestMethod.POST)
    public String endTheGoal(@RequestParam String UserName,@RequestParam String goalId) {
        System.out.println("data from client------VVVV");
        System.out.println(UserName);

        User user =uRepo.findByUsername(UserName);
    	Goal currentgoal = gRepo.findCurrentGoal(user.getId());
        currentgoal.setStatus(StatusEnum.CANCELLED);
        gService.cancelGoal(currentgoal);
        return "end goal successfully";
    }
	
	
	@RequestMapping(value = "/completedGoal",method = RequestMethod.POST)
	public ResultJson viewCompletedGoal(@RequestParam String UserName){

		User user =uRepo.findByUsername(UserName);
    	List<Goal> completedGoals = gRepo.findCompletedGoals(user.getId());

    	return new ResultJson(200,"get completedgoals successfully",completedGoals);
    
}
	
	@RequestMapping(value = "/pastGoal",method = RequestMethod.POST)
	public ResultJson viewPastGoal(@RequestParam String UserName){

		User user =uRepo.findByUsername(UserName);
    	List<Goal> pastGoals = gRepo.findPastGoals(user.getId());

    	return new ResultJson(200,"get pastGoals successfully",pastGoals);
    
}
} 

