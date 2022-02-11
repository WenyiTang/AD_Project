package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.MealEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealEntryRepo extends JpaRepository<MealEntry, Integer>{

    @Query("SELECT me.trackScore FROM MealEntry me WHERE me.author.id = :id")
    List<Integer> findUserMealEntryTrackScore(@Param("id") int id);
}
