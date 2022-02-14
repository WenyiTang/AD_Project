package com.example.adproject.controller;


import com.example.adproject.api.ResultJson;
import com.example.adproject.helper.MealEdit;
import com.example.adproject.model.Comment;
import com.example.adproject.model.Goal;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class ViewPastMealsController {

    @Autowired
    UserRepo uRepo;

    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    GoalRepo GRepo;


    @GetMapping("/pastmeals")
    public String viewUserPastMeals(Model model,Principal principal) {

        User user = uRepo.findByUsername(principal.getName());
        if (user == null) {
            return null;
        }
        List<MealEntry> entries = mRepo.findVisibleMealEntryByAuthor(user);

        String goalStr = "";
        if (user.getGoals().size() > 0){
            Goal goal = GRepo.findCurrentGoal(user.getId());
            if (goal!= null){
                goalStr = goal.getGoalDescription();
            }

        }

        model.addAttribute("entries", entries);
        model.addAttribute("user", user);
        model.addAttribute("goal", goalStr);

        return "./meals/meals_list";
    }


    @GetMapping("/view/mealDetail/{id}")
    public String viewMealEntry(Principal principal, Model model, @PathVariable Integer id) {
        MealEntry entry = mRepo.findById(id).get();
        if(entry == null) {
            return null;
        }
        User user = entry.getAuthor();

        model.addAttribute("entry", entry);
        model.addAttribute("user",user);
//        ArrayList<String> eidList = new ArrayList<String>(Arrays.asList("Public","Private"));
//        model.addAttribute("privateState", eidList);

        return "./meals/mealsDetail";
    }


    @GetMapping("/deleteMeal/{id}")
    public String DeleteMealEntry(Principal principal, Model model, @PathVariable Integer id) {
        User user = uRepo.findByUsername(principal.getName());
        if (user != null){
            MealEntry deleteMeal = mRepo.findMealEntryByMealId(id);
            if (deleteMeal != null){
                mRepo.deleteMealById(id);
            }else {

            }
        }
        return "redirect:/meals/pastmeals";
    }


    @RequestMapping(value = "/editmeal/{id}", method = RequestMethod.GET)
    public String editEntry(Principal principal, Model model, @PathVariable Integer id) {

        MealEntry entry = mRepo.findById(id).get();
        if(entry == null) {
            return null;
        }
//        model.addAttribute("entry", entry);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = entry.getTimeStamp();
        String localTime = df.format(time);

        MealEdit mealedit = new MealEdit(entry.getId(),localTime,entry.getDescription());
        model.addAttribute("entry", mealedit);

        return "./meals/editMeal";
    }


    @RequestMapping(value = "/editmeal/{id}", method = RequestMethod.POST)
    public String submiteditEntry(@ModelAttribute @Validated MealEdit entry,
                                    Principal principal) throws IOException {

        User user = uRepo.findByUsername(principal.getName());
        if (user != null){
            MealEntry editedMeal = mRepo.findMealEntryByMealId(Integer.valueOf(entry.getId()));
            if (editedMeal != null){
                editedMeal.setDescription(entry.getDescription());
//                String testStr = "2022-02-07T10:26:42.902";
//                String newStr = testStr.substring(0,19).replaceAll("T"," ");
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                LocalDateTime testTime = LocalDateTime.parse(newStr, df);

                DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(entry.getMealTime(), timeDtf);

                editedMeal.setTimeStamp(localDateTime);

                mRepo.saveAndFlush(editedMeal);

            }else {



            }

        }else {

        }

        return "redirect:/meals/view/mealDetail/" + entry.getId();

    }



}
