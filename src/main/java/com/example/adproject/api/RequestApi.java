package com.example.adproject.api;

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

    @Value("${upload.file.path}")
    private String uploadPathStr;

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
//                String timeStr = mealTime;
//                DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                LocalDateTime localDateTime = LocalDateTime.parse(timeStr, timeDtf);
////                ZoneId zone = ZoneId.systemDefault();
////                Instant instant = localDateTime.atZone(zone).toInstant();
////                Date date = Date.from(instant);
//                editedMeal.setTimeStamp(localDateTime);
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






    @PostMapping("/upload")
    public boolean upload(@RequestParam MultipartFile file, @RequestParam String filename)
    {
        if(file == null || file.isEmpty() || filename == null || filename.isEmpty())
            return false;
        try(InputStream inputStream = file.getInputStream())
        {
            Path uploadPath = Paths.get(uploadPathStr);
            if(!uploadPath.toFile().exists())
                uploadPath.toFile().mkdirs();
            Files.copy(inputStream, Paths.get(uploadPathStr).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("upload file , filename is "+filename);

            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/download")
    public ResponseEntity<FileSystemResource> download(@RequestParam String filename)
    {
        if(filename == null || filename.isEmpty())
            return null;
        File file = Paths.get(uploadPathStr).resolve(filename).toFile();
        if(file.exists() && file.canRead())
        {
            System.out.println("download file , filename is "+filename);
            return ResponseEntity.ok()
                    .contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(file));
        }
        else
            return null;
    }

}
