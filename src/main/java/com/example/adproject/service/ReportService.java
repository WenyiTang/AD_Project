package com.example.adproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.Report;

public interface ReportService {
	
	List<Report> findReportsByStatus(ReportEnum status);
	
	Report findReportById(Integer id);
	
	void save(Report r);
	
	List<Report> findReportsByStatusIdReason(ReportEnum status, Integer id, String reason);
}