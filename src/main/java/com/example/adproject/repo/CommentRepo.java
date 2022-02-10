package com.example.adproject.repo;

import java.util.List;

import com.example.adproject.model.Comment;
import com.example.adproject.model.MealEntry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where id = :id")
	public Comment findCommentById(@Param("id") Integer id);

	@Query("SELECT c FROM Comment c WHERE c.mealEntry = :mealEntry")
	public List<Comment> findCommentByMealEntry(@Param("mealEntry") MealEntry mealEntry);
}
