package com.example.adproject.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adproject.model.MealEntry;
import com.example.adproject.repo.MealEntryRepo;

@Service
@Transactional
public class MealEntryServiceImpl implements MealEntryService {
	@Autowired
	MealEntryRepo mrepo;

	public MealEntry findMealEntryById(Integer id) {
		return mrepo.findById(id).get();
	}
}
