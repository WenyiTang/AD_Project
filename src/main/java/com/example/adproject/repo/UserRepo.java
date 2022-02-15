package com.example.adproject.repo;

import com.example.adproject.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.username = :username ")
	User findByUsername(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	User findByResetPasswordToken(String token);

	@Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
	List<User> findUserWithUsernameLike(@Param("username") String username);

	@Query(value = "SELECT * FROM user WHERE user_id IN (SELECT user_id FROM users_roles JOIN role r on users_roles.role_id = r.role_id WHERE type = 'USER')", nativeQuery = true)
	List<User> findAllUsersOnly();
}
