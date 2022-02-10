package com.example.adproject.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.adproject.model.User;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MakeshiftLoginController {
    @Autowired
    UserRepo uRepo;

    @Autowired
    UserService uService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession principal,
                        Model model) 
    {
        User user = uRepo.findByUsername(username);
        if(user == null) {
            return "index";
        }

        if(password.equals(user.getPassword())){
            principal.setAttribute("username", username);
            principal.setAttribute("id", user.getId());
            return "redirect:/friends";
            
        }
        else{
            return "index";
        }
    }

    @GetMapping("/friends")
    public String friendsList(Principal principal, Model model) {
        String username = principal.getName();

        User user = uRepo.findByUsername(username);

        List<User> friends = uService.findFriendsOf(user);

        model.addAttribute("user", user);
        model.addAttribute("friends", friends);


        return "./blog/friends_list";
    }
}
