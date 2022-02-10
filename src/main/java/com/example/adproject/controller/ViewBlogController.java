package com.example.adproject.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.adproject.model.Comment;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.CommentRepo;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.MealEntryService;

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

    @Autowired
    MealEntryService mService;
    


    @GetMapping("/view/{id}")
    public String viewUserBlog(HttpSession session, Model model, @PathVariable Integer id) {
        String activeUsername = session.getAttribute("username").toString();
        User activeUser = uRepo.findByUsername(activeUsername);
        User user = uRepo.findById(id).get();
        if(user == null) {
            return null;
        }

        List<MealEntry> entries = mRepo.findVisibleMealEntryByAuthor(user);
 
        model.addAttribute("entries",entries);
        model.addAttribute("user",user);
        model.addAttribute("mService",mService);
        model.addAttribute("activeUser", activeUser);
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
        
        // Reverse order of comments so that it shows most recent on top 
        Collections.reverse(comments);

        model.addAttribute("entry", entry);
        model.addAttribute("user",user);
        model.addAttribute("comments",comments);
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("mService",mService);


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

    @GetMapping("/report/entry/{id}")
    public String flagEntry(HttpSession session, Model model, @PathVariable Integer id) {

        MealEntry entry = mRepo.findById(id).get();
        
        String activeUsername = session.getAttribute("username").toString();
        User activeUser = uRepo.findByUsername(activeUsername);
        if(entry == null) {
            return null;
        }
        User user = entry.getAuthor();

        model.addAttribute("entry", entry);
        model.addAttribute("user",user);
        model.addAttribute("activeUser",activeUser);

        return "./blog/flag_entry";
    }

    @GetMapping("/feed")
    public String viewUserFeed(HttpSession session, Model model){
        String activeUsername = session.getAttribute("username").toString();
        User activeUser = uRepo.findByUsername(activeUsername);
        if(activeUser == null) {
            return null;
        }

        model.addAttribute("user", activeUser);



        return "./blog/social_feed";
        
    }
    
}
