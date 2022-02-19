package com.example.adproject.api;
import com.example.adproject.helper.BlogEntry;
import com.example.adproject.helper.PastMeal;
import com.example.adproject.helper.StatusEnum;
import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import org.apache.commons.io.IOUtils;
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
import java.util.*;

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


        User user = uRepo.findByUsername(UserName);

        Goal goal = new Goal();
        goal.setGoalDescription(goalDescription);
        goal.setTotalMealCount(Integer.valueOf(totalMealCount));
        goal.setTargetCount(Integer.valueOf(targetCount));
        goal.setStatus(StatusEnum.IN_PROGRESS);
        goal.setAuthor(user);
        gRepo.saveAndFlush(goal);
        if (user != null){
            user.getGoals().add(goal);
            uRepo.saveAndFlush(user);
        }else {
            return ResultJson.error("User does not exist");
        }
        return new ResultJson(200,"set goal successfully");
    }


    @RequestMapping(value = "/pastMeals",method = RequestMethod.POST)
    public Object viewPastMeals(@RequestParam String UserName){
        User user = uRepo.findByUsername(UserName);
        if (user != null){
            List<MealEntry> allMeals = user.getEntries();

            ArrayList<PastMeal> mealEntries = new ArrayList<PastMeal>();

            for(MealEntry mealEntry : allMeals) {
                mealEntries.add(new PastMeal(mealEntry.getId(),mealEntry.getImageURL(), mealEntry.getFilename(),
                        mealEntry.isVisibility(),mealEntry.getTitle(),mealEntry.getDescription(),mealEntry.getTrackScore(),mealEntry.getTimeStamp()));
                }

            String goalStr = " ";
                Goal goal = gRepo.findCurrentGoal(user.getId());
                if (goal!= null){
                    goalStr = goal.getGoalDescription();
                }
            

            Map<String,Object> result = new HashMap<>();
            result.put("data",mealEntries);
            result.put("goalStr",goalStr);

            return result;
        }

        return null;

    }


    @RequestMapping(value = "/currentGoal",method = RequestMethod.POST)
    public String viewCurrentGoal(@RequestParam String UserName){
        User user = uRepo.findByUsername(UserName);
        if (user != null){
            String goalStr = "";
            if (user.getGoals().size() > 0){
                Goal goal = gRepo.findCurrentGoal(user.getId());
                if (goal!= null){
                    goalStr = goal.getGoalDescription();
                }
            }

            return goalStr;
        }else {
            return " ";
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
                String timeStr = mealTime + ":00";

//                String testStr = "2022-02-07T10:26:42.902";
//                String newStr = testStr.substring(0,19).replaceAll("T"," ");
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                LocalDateTime testTime = LocalDateTime.parse(newStr, df);

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

    @GetMapping(value = "/foodImage/get",produces = MediaType.IMAGE_JPEG_VALUE,params = {"imagePath"})
    public @ResponseBody byte[] getInageWithMediaType(@RequestParam String imagePath) throws IOException{
        InputStream in = getClass().getResourceAsStream(imagePath);
        return IOUtils.toByteArray(in);

    }



}
