package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	@Query("select c from Comment c where id = :id")
	public Comment findCommentById(@Param("id") Integer id);
}
