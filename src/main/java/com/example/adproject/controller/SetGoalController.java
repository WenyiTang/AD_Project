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
    public String addGoal(HttpSession session,@ModelAttribute("goal") Goal goal){
        //from session get User Id or UserName, generate the user_goals data

//        String Username = session.getAttribute("username").toString();
        User user = uRepo.findByUsername("Ken");
        if (user != null){
            user.getGoals().add(goal);
            uRepo.saveAndFlush(user);
        }
        return "redirect:/meals/pastmeals";
    }


}
