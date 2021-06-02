package com.example.REGISTRATION.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.UserRepo;
import com.example.REGISTRATION.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	/*DELETE USER*/
	@PostMapping("/deleteUserById/{id}")
	public void deleteUser(@PathVariable Long id) {
		User user = userRepo.findUserById(id);
		userService.deleteUser(user.getId());
	}
	
	
}
