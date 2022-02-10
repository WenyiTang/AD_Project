package com.example.adproject.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;
import com.example.adproject.repo.ReportRepo;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
	@Autowired
	ReportRepo rrepo;
	
	public List<Report> findReportsByStatus(ReportEnum status){
		return rrepo.findReportsByStatus(status);
	}
	
	public Report findReportById(Integer id) {
		return rrepo.findById(id).get();
	}
	
	public void save(Report r) {
		rrepo.save(r);
	}
	
	public List<Report> findReportsByStatusEntryReason(ReportEnum status, MealEntry entry, String reason){
		return rrepo.findReportsByStatusEntryReason(status, entry, reason);
	}
	
	public List<Report> findPendingNProgressReports(User admin){
		return rrepo.findPendingNProgressReports(admin);
	}
	
	public List<Report> findPendingNProgressReportsEntryReason(MealEntry entry, String reason){
		return rrepo.findPendingNProgressReportsEntryReason(entry, reason);
	}
}
