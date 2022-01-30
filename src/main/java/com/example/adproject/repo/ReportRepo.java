package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.Report;

public interface ReportRepo extends JpaRepository<Report, Integer>{

}
