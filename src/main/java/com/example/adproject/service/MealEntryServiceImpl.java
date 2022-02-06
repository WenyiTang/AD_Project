package com.example.adproject.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.MealEntryRepo;

@Transactional
@Service
public class MealEntryServiceImpl implements MealEntryService{

	@Autowired
	MealEntryRepo mrepo;
	
	public List<MealEntry> findMealEntryByUserId(Integer id){
		return mrepo.findMealEntryByUserId(id);
	}
}
