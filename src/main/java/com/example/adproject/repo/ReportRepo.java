package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;
import com.example.adproject.model.User;

public interface ReportRepo extends JpaRepository<Report, Integer>{

	@Query("select r from Report r where r.status = :status")
	public List<Report> findReportsByStatus(@Param("status") ReportEnum status);
	
	@Query("select r from Report r where r.status = :status and r.mealEntry = :entry and r.reason = :reason")
	public List<Report> findReportsByStatusEntryReason(@Param("status") ReportEnum status, 
			@Param("entry") MealEntry entry, @Param("reason") String reason);
	
	@Query("select r from Report r where r.status = 'PENDING' or (r.status = 'IN_PROGRESS' and r.resolvedBy = :admin)")
	public List<Report> findPendingNProgressReports(@Param("admin") User admin);
	
	@Query("select r from Report r where "
			+ "(r.status = 'PENDING' or r.status = 'IN_PROGRESS') "
			+ "and r.mealEntry = :entry and "
			+ "r.reason = :reason")
	public List<Report> findPendingNProgressReportsEntryReason(@Param("entry") MealEntry entry, @Param("reason") String reason);
	
}
