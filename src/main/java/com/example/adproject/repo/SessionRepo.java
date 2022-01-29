
package com.example.adproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.adproject.model.Session;

public interface SessionRepo extends JpaRepository<Session, Integer>{

	@Query("select s from Session s where id = :id")
	public Session findSessionById(@Param("id") Integer id);
}