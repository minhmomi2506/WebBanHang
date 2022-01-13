package com.example.Minh.WebSpringBoot.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Minh.WebSpringBoot.Entity.User;
import com.example.Minh.WebSpringBoot.Repo.UserRepo;
import com.example.Minh.WebSpringBoot.Service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

//	@Autowired
//	private UserRepo userRepo;

	@GetMapping("/listUsers")
	public List<User> getAll(Principal principal) {
		String userId = principal.getName();
		User user = userRepo.getUserByUserId(userId);
		return userService.getAll(user);
	}

	@DeleteMapping("/deleteById/{id}")
	public void deleteById(@PathVariable Long id) {
		userService.deleteById(id);
	}

	@GetMapping("/userInformation")
	public User userInformation(Principal principal) {
		String userId = principal.getName();
		User user = userRepo.getUserByUserId(userId);
		return userService.information(user);
	}

	@PutMapping("/editUserInformation/{id}")
	public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
		User user1 = userService.editUserInformation(id, user);
		return new ResponseEntity<User>(user1, HttpStatus.OK);
	}
}
