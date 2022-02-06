package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.Report;

import org.springframework.stereotype.Component;

@Component
public interface ReportService {

    public Report submitReport(Integer userId, Integer mealEntryId, String reason);

    public List<Report> getReportsByMealEntryId(Integer mealEntryId);

    public void deleteReportsByMealEntryId(Integer mealEntryId);
    
}
