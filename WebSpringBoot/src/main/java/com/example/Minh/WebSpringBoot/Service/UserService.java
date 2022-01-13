package com.example.Minh.WebSpringBoot.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.Minh.WebSpringBoot.Entity.User;
import com.example.Minh.WebSpringBoot.Repo.UserRepo;

@Service
@Component
@Transactional
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepo.getUserByUserId(userId);
		if(user == null) {
			throw new UsernameNotFoundException("Khong tim thay");
		}
		return new MyUserDetails(user);
	}
	
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
//	public List<Integer> sumMoneyOfEachUser(){
//		List<User> users = userRepo.findAll();
//	}
	
	public User findUserById(Long id) {
		return userRepo.findUserById(id);
	}
	
	public User findUserByEmail(String userId) {
		return userRepo.getUserByUserId(userId);
	}
	
	/*REGISTER*/
	public void saveUserToDB(MultipartFile image, User user) {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			user.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}
	
	public List<User> getAll(User user1){
		List<User> users = new ArrayList<User>();
		List<User> users1 = userRepo.findAll();
		for(User user : users1) {
			if(user1 != user) {
				users.add(user);
			}
		}
		return users;
	}
	
	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}
	
	public User information(User user) {
		return user;
	}
	
	public User editUserInformation(Long id , User user) {
		User user1 = userRepo.findUserById(id);
		user1.setFullName(user.getFullName());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setEmail(user.getEmail());
		user1.setAddress(user.getAddress());
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String encodedPassword = encoder.encode(password);
//		user.setPassword(encodedPassword);
		userRepo.save(user1);
		return user1;
	}
}
