package com.example.adproject.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.MealEntry;
import java.util.List;
import com.example.adproject.model.User;
import org.springframework.transaction.annotation.Transactional;


public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

    @Query("SELECT m FROM MealEntry m where m.visibility = true AND m.author = :author")
    List<MealEntry> findVisibleMealEntryByAuthor(@Param("author") User author);

    @Query("SELECT m FROM MealEntry m where m.visibility = true")
    List<MealEntry> findAllVisibleMealEntries();

    @Query("Select me from MealEntry me where me.author.id = :userid")
	public List<MealEntry> findEntryByAuthor(@Param("userid") Integer userId);

    @Query("SELECT m FROM MealEntry m where m.id = :mealId")
    MealEntry findMealEntryByMealId(@Param("mealId") Integer mealId);
  
    @Query("SELECT me.trackScore FROM MealEntry me WHERE me.author.id = :uid AND me.goal.id = :gid")
    public List<Integer> findMealEntryTrackScoreByUserAndGoalId(@Param("uid") int uid, @Param("gid") int gid);
    
   
    @Transactional
    @Modifying
    @Query("DELETE FROM MealEntry m where m.id = :mealId")
    void deleteMealById(@Param("mealId") Integer mealId);


    @Transactional
    @Modifying
    @Query("DELETE FROM MealEntry m where m.id = :mealId")
    void updateMealBy(@Param("mealId") Integer mealId);

}
