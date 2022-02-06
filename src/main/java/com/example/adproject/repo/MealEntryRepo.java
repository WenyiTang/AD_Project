package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.MealEntry;

public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

	@Query("Select m from MealEntry m where m.author.id = :userid")
	public List<MealEntry> findMealEntryByUserId(@Param("userid") Integer id);
}
