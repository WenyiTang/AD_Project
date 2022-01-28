package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer>{
	
	@Query("select a from Admin a where id = :id")
	public Admin findAdminById(@Param("id") Integer id);
}
