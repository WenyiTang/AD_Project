package com.example.adproject.controller;


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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
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

    @Autowired
    UserService uService;

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

        String activeUsername = principal.getName();
        if(entry == null) {
            return null;
        }
        User user = entry.getAuthor();

        model.addAttribute("entry", entry);
        model.addAttribute("user",user);
        return "./meals/mealsDetail";

    }


}
