package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;
import com.example.adproject.repo.ReportRepo;
import com.example.adproject.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportServiceImpl implements ReportService {
    @Autowired 
    ReportRepo rRepo;

    @Autowired
    MealEntryRepo mRepo;

    @Autowired
    UserRepo uRepo;

    @Override
    public Report submitReport(Integer userId, Integer mealEntryId, String reason) {
        User reporter = uRepo.findById(userId).get();
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();

        if(reporter == null || mealEntry == null) {
            return null;
        }
        Report report = new Report(reason, mealEntry, reporter);

        rRepo.saveAndFlush(report);


        return report;
    }

    @Override
    public List<Report> getReportsByMealEntryId(Integer mealEntryId) {

        
        MealEntry mealEntry = mRepo.findById(mealEntryId).get();
        if(mealEntry == null){
            return null;
        }
    

        return rRepo.findReportsByMealEntry(mealEntry);
    }

    @Override
    public void deleteReportsByMealEntryId(Integer mealEntryId) {
        List<Report> reports = getReportsByMealEntryId(mealEntryId);
        if (reports == null) {
            return;
        }
        for(Report report : reports) {
            rRepo.delete(report);
        }
        
    }
    
}
