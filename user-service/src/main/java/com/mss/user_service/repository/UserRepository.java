package com.mss.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mss.user_service.entity.Request;
import com.mss.user_service.entity.User;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<Request, Long>{
	@Query("select u from User u where u.email = :email")
	public Optional<User> findByEmail(@Param("email") String email);
	
	@Query("select u from User u where u.id = :userId")
	public Optional<User> findByUserId(@Param("userId") Long userId);
}
