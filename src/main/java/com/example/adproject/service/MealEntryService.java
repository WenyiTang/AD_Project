package com.example.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
