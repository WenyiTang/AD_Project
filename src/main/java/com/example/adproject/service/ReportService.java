package com.example.adproject.service;

import java.util.List;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;

import org.springframework.stereotype.Component;

@Component
public interface ReportService {

    public Report submitReport(Integer userId, Integer mealEntryId, String reason);

    public List<Report> getReportsByMealEntryId(Integer mealEntryId);

    public void deleteReportsByMealEntryId(Integer mealEntryId);
    
    public List<Report> findReportsByStatus(ReportEnum status);

    public List<Report> findPendingNProgressReports(User admin);

    public Report findReportById(Integer id);

    public void save(Report r);

	public List<Report> findReportsByStatusEntryReason(ReportEnum status, MealEntry entry, String reason);

	public List<Report> findPendingNProgressReportsEntryReason(MealEntry entry, String reason);
}
