package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.helper.ReportEnum;
import com.example.adproject.model.Report;

public interface ReportRepo extends JpaRepository<Report, Integer>{

	@Query("select r from Report r where r.status = :status")
	public List<Report> findReportsByStatus(@Param("status") ReportEnum status);
	
	@Query("select r from Report r where r.status = :status and r.mealEntry.id = :id and r.reason = :reason")
	public List<Report> findReportsByStatusIdReason(@Param("status") ReportEnum status, 
			@Param("id") Integer id, @Param("reason") String reason);
}
