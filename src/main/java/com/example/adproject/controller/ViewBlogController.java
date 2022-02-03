package com.example.adproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.adproject.model.Comment;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blog")
public class ViewBlogController {
    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Autowired
    CommentRepo cRepo;
    


    @GetMapping("/view/{id}")
    public String viewUserBlog(Model model, @PathVariable Integer id) {

        
        User user = uRepo.findById(id).get();
        if(user == null) {
            return null;
        }

        List<MealEntry> entries = mRepo.findVisibleMealEntryByAuthor(user);
 
        model.addAttribute("entries",entries);
        model.addAttribute("user",user);
        return "./blog/food_blog";
    } 

    @GetMapping("/view/entry/{id}")
    public String viewMealEntry(HttpSession session, Model model, @PathVariable Integer id) {
        MealEntry entry = mRepo.findById(id).get();
        
        String activeUsername = session.getAttribute("username").toString();
        User activeUser = uRepo.findByUsername(activeUsername);
        if(entry == null) {
            return null;
        }
        User user = entry.getAuthor();
        
        List<Comment> comments = cRepo.findCommentByMealEntry(entry);

        model.addAttribute("entry", entry);
        model.addAttribute("user",user);
        model.addAttribute("comments",comments);
        model.addAttribute("activeUser",activeUser);


        return "./blog/meal_entry";
        
    }

    @PostMapping("/comment/{id}")
    public String addComment(HttpSession session, Model model, @PathVariable Integer id, @RequestParam String caption) {
        MealEntry entry = mRepo.findById(id).get();

        String activeUsername = session.getAttribute("username").toString();
        User activeUser = uRepo.findByUsername(activeUsername);

        Comment comment = new Comment(caption,activeUser,entry);

        cRepo.saveAndFlush(comment);

        return "redirect:/blog/view/entry/" + id.toString();
    }
    
}
