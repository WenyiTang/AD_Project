package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.MealEntry;

public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

}
