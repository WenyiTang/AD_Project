package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
