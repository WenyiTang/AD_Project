package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adproject.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

	Role findByType(String type); 
}
