package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;


public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

	@Query("Select me from MealEntry me where me.author.id = :userid")
	public List<MealEntry> findEntryByAuthor(@Param("userid") Integer userId);

}
