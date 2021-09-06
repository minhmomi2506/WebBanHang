package com.example.REGISTRATION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.UserRepo;
import com.example.REGISTRATION.service.UserService;

@RestController
public class UserController {
	/* EDIT USER INFORMATION */

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	/*LIST USERS*/
	@GetMapping("/listAllUsers")
	public List<User> listAllUsers(){
		List<User> users = userRepo.findAll();
		return users;
	}

	/* EDIT USER INFORMATION */
	@PutMapping("/editUser/{id}")
	public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
		User user1 = userService.editUserInformation(id, user);
		return new ResponseEntity<User>(user1, HttpStatus.OK);
	}

	/* EDIT USER PASSWORD */
	@PutMapping("/editUserPassword/{id}")
	public ResponseEntity<User> editUserPassword(@PathVariable Long id, @RequestBody User user) {
		User user1 = userService.editUserPassword(id, user);
		return new ResponseEntity<User>(user1, HttpStatus.OK);
	}
}
