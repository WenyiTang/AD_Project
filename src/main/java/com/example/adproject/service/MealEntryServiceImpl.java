package com.example.adproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;
import com.example.adproject.repo.MealEntryRepo;


@Service
public class MealEntryServiceImpl implements MealEntryService {

	@Autowired
	private MealEntryRepo repository;

	@Transactional
	public List<MealEntry> findEntryByAuthor(Integer userId){
		return repository.findEntryByAuthor(userId);
	}

	
}
