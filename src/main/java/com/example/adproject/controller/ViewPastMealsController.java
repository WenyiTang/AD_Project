package com.example.adproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meals")
public class ViewPastMealsController {

    @GetMapping("/list")
    public String pastMealsList(Model model){


        return "./meals/meals_list";
    }

}
