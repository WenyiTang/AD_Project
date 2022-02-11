package com.example.adproject.repo;

import com.example.adproject.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User, Integer>{

    @Query("SELECT u FROM User u WHERE u.username = :username ")
    User findByUsername(@Param("username") String username);


}
