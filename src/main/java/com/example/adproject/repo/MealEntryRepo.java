package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.MealEntry;
import com.example.adproject.model.User;



public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

    @Query("SELECT m FROM MealEntry m where m.visibility = true AND m.author = :author")
    List<MealEntry> findVisibleMealEntryByAuthor(@Param("author") User author);

    @Query("SELECT m FROM MealEntry m where m.visibility = true")
    List<MealEntry> findAllVisibleMealEntries();

    @Query("Select m from MealEntry m where m.author.id = :userid")
	List<MealEntry> findEntryByAuthor(@Param("userid") Integer userId);


  

}
