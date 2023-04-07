package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entity.User;

public interface PasswordResetTokenRepo extends JpaRepository<User, Integer> {

	@Query(value="select * from user where email_id=?1",nativeQuery=true)
	User findByEmail(String email);
	
	@Query(value = "select * from user where token=?1", nativeQuery = true)
	User findByToken(String token);
}
