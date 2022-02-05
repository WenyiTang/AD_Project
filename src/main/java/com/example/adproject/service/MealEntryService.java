package com.example.adproject.service;

import java.beans.JavaBean;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.adproject.highchart.Employee;
import com.example.adproject.highchart.EmployeeRepository;
import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.MealEntryRepo;


@Service
public class MealEntryService {
	
	@Autowired
	private MealEntryRepo repository;
	
	public List<MealEntry> getAllEntry(){
		return repository.findAll();
	}
	

}
