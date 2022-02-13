package com.example.adproject.api;
import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RequestApi {


    @Autowired
    GoalRepo gRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    MealEntryRepo mRepo;

    @RequestMapping(value = "/setGoal",method = RequestMethod.POST)
    public ResultJson generateUserGoal(@RequestParam String UserName, @RequestParam String goalDescription,
                                       @RequestParam String totalMealCount, @RequestParam String targetCount) {
        // write your code ....
        System.out.println("data from client------VVVV");
        System.out.println(UserName);

        Goal goal = new Goal();
        goal.setGoalDescription(goalDescription);
        goal.setTotalMealCount(Integer.valueOf(totalMealCount));
        goal.setTargetCount(Integer.valueOf(targetCount));
        goal.setStatus(StatusEnum.IN_PROGRESS);

        User user = uRepo.findByUsername(UserName);
        if (user != null){
            user.getGoals().add(goal);
            uRepo.saveAndFlush(user);
        }else {
            return ResultJson.error("User does not exist");
        }
        return new ResultJson(200,"set goal successfully");
    }


    @RequestMapping(value = "/pastMeals",method = RequestMethod.POST)
    public ResultJson viewPastMeals(@RequestParam String UserName){
        User user = uRepo.findByUsername(UserName);
        if (user != null){
            List<MealEntry> allMeals = user.getEntries();
            if (allMeals.size() == 0){
                allMeals = new ArrayList<MealEntry>();
            }
            return new ResultJson(200,"get meals successfully",allMeals);
        }else {
            return ResultJson.error("User does not exist");
        }
    }


    @RequestMapping(value = "/modifyMealInfo",method = RequestMethod.POST)
    public ResultJson modifyMeals(@RequestParam String UserName,@RequestParam String mealTime,
                                  @RequestParam String mealDes,@RequestParam String publicStates,
                                  @RequestParam String mealId){
        User user = uRepo.findByUsername(UserName);
        if (user != null){
            MealEntry editedMeal = mRepo.findMealEntryByMealId(Integer.valueOf(mealId));
            if (editedMeal != null){
                editedMeal.setDescription(mealDes);
                String timeStr = mealTime;

                DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(timeStr, timeDtf);
                editedMeal.setTimeStamp(localDateTime);
                if (publicStates.equals("1")){
                    editedMeal.setVisibility(true);
                }else if (publicStates.equals("0")){
                    editedMeal.setVisibility(false);
                }
                mRepo.saveAndFlush(editedMeal);

                return new ResultJson(200,"delete meal successfully");
            }else {


                return new ResultJson(300,"this meal does not exist");
            }

        }else {
            return ResultJson.error("User does not exist");
        }
    }

    @RequestMapping(value = "/deleteMeal",method = RequestMethod.POST)
    public ResultJson deleteMeals(@RequestParam String UserName,@RequestParam String mealId){
        User user = uRepo.findByUsername(UserName);
        if (user != null){
            MealEntry deleteMeal = mRepo.findMealEntryByMealId(Integer.valueOf(mealId));
            if (deleteMeal != null){
                mRepo.deleteMealById(Integer.valueOf(mealId));
                return new ResultJson(200,"delete meal successfully");
            }else {
              return new ResultJson(300,"this meal does not exist");
            }

        }else {
            return ResultJson.error("User does not exist");
        }
    }


}
