package com.example.adproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;

public interface ReportService {
	
	List<Report> findReportsByStatus(ReportEnum status);
	
	List<Report> findPendingNProgressReports(User admin);
	
	Report findReportById(Integer id);
	
	void save(Report r);
	
	List<Report> findReportsByStatusEntryReason(ReportEnum status, MealEntry entry, String reason);
	
	List<Report> findPendingNProgressReportsEntryReason(MealEntry entry, String reason);
}