package com.example.adproject.repo;

import java.util.List;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepo extends JpaRepository<Report, Integer>{

    @Query("SELECT r FROM Report r WHERE r.mealEntry = :mealEntry")
    public List<Report> findReportsByMealEntry(@Param("mealEntry") MealEntry mealEntry);

}
