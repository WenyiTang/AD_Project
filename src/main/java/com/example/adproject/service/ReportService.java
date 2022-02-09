package com.example.adproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;

public interface ReportService {
	
	List<Report> findReportsByStatus(ReportEnum status);
	
	List<Report> findPendingNProgressReports(User admin);
	
	Report findReportById(Integer id);
	
	void save(Report r);
	
	List<Report> findReportsByStatusIdReason(ReportEnum status, Integer id, String reason);
	
	List<Report> findPendingNProgressReportsIdReason(Integer id, String reason);
	
	
}