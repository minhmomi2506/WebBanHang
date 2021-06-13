package com.example.REGISTRATION.service;

import java.io.IOException;
import java.util.Base64;

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

import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.loginWithSocial.AuthenProvider;
import com.example.REGISTRATION.repo.UserRepo;

@Service
@Component
@Transactional
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Khong tim thay");
		}
		return new MyUserDetails(user);
	}
	
	public User findUserById(Long id) {
		return userRepo.findUserById(id);
	}
	
	public User findUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}
	
	public void deleteUser(Long id) {
		userRepo.deleteUserById(id);
	}
	
	public void editUserInformation(User user , String fullName , String phoneNumber) {
		user.setFullName(fullName);
		user.setPhoneNumber(phoneNumber);
		userRepo.save(user);
	}
	
	public void editUserImage(MultipartFile file , User user) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		userRepo.save(user);
	}
	
	public void saveUserToDB(MultipartFile file, User user) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
	}
	
	public void processOAuthPostLogin(String email) {
		User existUser = userRepo.getUserByEmail(email);
		if(existUser == null) {
			User user = new User();
			user.setEmail(email);
			user.setAuthProvider(AuthenProvider.SOCIAL);
			user.setEnabled(true);
			userRepo.save(user);
		}
	}

	public void registerNewUserAfterOAuthLoginSuccess(String email, String name, AuthenProvider authProvider) {
		User user = new User();
		user.setEmail(email);
		user.setFullName(name);
		user.setEnabled(true);
		user.setAuthProvider(authProvider);
		userRepo.save(user);
	}

	public void updateExistUserAfterOAuthLoginSuccess(User user , String name , AuthenProvider authProvider) {
		// TODO Auto-generated method stub
		user.setFullName(name);
		user.setAuthProvider(authProvider);
		userRepo.save(user);
	}
}
