package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adproject.model.MealEntry;

@Repository
public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

}
