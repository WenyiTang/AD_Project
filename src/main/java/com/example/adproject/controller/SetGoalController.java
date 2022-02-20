package com.example.adproject.controller;

import com.example.adproject.model.Goal;
import com.example.adproject.model.User;
import com.example.adproject.repo.GoalRepo;
import com.example.adproject.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/goal")
public class SetGoalController {

    @Autowired
    GoalRepo gRepo;

    @Autowired
    UserRepo uRepo;

    @GetMapping("/loadgoal")
    public String loadSetGoal(Model model){
        Goal goal = new Goal();
        model.addAttribute("goal",goal);
        return "./goal/set_goal";
    }


    @PostMapping("/setgoal")
    public String addGoal(Principal principal, @ModelAttribute("goal") Goal goal){
        User user = uRepo.findByUsername(principal.getName());
        if (user != null){

            if (user.getGoals().size() > 0){
                Goal currentGoal = gRepo.findCurrentGoal(user.getId());
                if (currentGoal != null){
                    // current have goal in progress, show error message
                    
                    return "";
                }

            }
            user.getGoals().add(goal);
            uRepo.saveAndFlush(user);
        }

        return "redirect:/meals/pastmeals";
    }


}
