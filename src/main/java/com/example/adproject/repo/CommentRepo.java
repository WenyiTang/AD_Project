package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
