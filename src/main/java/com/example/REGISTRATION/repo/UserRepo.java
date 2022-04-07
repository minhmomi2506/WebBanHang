package com.example.REGISTRATION.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User getUserByUsername(String username);
	User findUserById(Long id);
	
	@Transactional
	@Query("Delete from User where id = ?1")
	@Modifying
	
	void deleteUserById(Long id);
	
	User findUserByUsername(String username);
}
