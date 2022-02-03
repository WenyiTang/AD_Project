package com.example.adproject.controller;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.CommentRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class ViewBlogController {
    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    CommentRepo cRepo;
    


    @GetMapping("/view")
    public String viewUserBlog(Model model) {

        //
        User user = uRepo.findByUsername("Monica");
        if(user == null) {
            return null;
        }

        List<MealEntry> entries = mRepo.findVisibleMealEntryByAuthor(user);
 
        model.addAttribute("entries",entries);
        model.addAttribute("user",user);
        return "./blog/food_blog";
    } 

    @GetMapping("/view/{id}")
    public String viewMealEntry(Model model, @PathVariable Integer id) {
        MealEntry entry = mRepo.findById(id).get();

        if(entry == null) {
            return null;
        }
        User user = entry.getAuthor();

        model.addAttribute("entry", entry);
        model.addAttribute("user",user);

        return "./blog/meal_entry";
        
    }
    
}
