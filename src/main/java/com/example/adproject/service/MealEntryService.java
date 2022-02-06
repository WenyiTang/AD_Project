package com.example.adproject.service;

import java.util.List;

import com.example.adproject.model.MealEntry;

public interface MealEntryService {

	public List<MealEntry> findMealEntryByUserId(Integer id);
}