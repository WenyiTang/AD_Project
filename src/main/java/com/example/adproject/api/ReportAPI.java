package com.example.adproject.api;

import com.example.adproject.model.Report;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.ReportRepo;
import com.example.adproject.repo.UserRepo;
import com.example.adproject.service.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportAPI {


    @Autowired
    ReportService rService;

    @PostMapping("/submit")
    public Report submitReport(@RequestParam Integer userId, 
                                 @RequestParam Integer mealEntryId,
                                 @RequestParam String reason)
    {
        return rService.submitReport(userId, mealEntryId, reason);
    }
    
}
