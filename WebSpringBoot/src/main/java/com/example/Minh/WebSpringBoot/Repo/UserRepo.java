package com.example.Minh.WebSpringBoot.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Minh.WebSpringBoot.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User getUserByUserId(String username);

	User findUserById(Long id);

}
