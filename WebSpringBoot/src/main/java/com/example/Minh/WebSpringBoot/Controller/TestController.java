package com.example.Minh.WebSpringBoot.Controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.Minh.WebSpringBoot.Entity.User;
import com.example.Minh.WebSpringBoot.Repo.UserRepo;
import com.example.Minh.WebSpringBoot.Service.UserService;

@Controller
public class TestController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/login")
	public String login(User user, HttpServletRequest request, Model model) {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen == null || authen instanceof AnonymousAuthenticationToken || request.getSession() == null) {
			return "login/loginPage";
		}
		model.addAttribute("user", user);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", user);
		return "redirect:/";
	}
	
	@PostMapping("/save")
	public String save(MultipartFile file, User user) {
		userService.saveUserToDB(file, user);
		return "redirect:/login";
	}
	
	@GetMapping("/")
	public String home( Model model, Principal principal) {
		String userId = principal.getName();
		User user = userRepo.getUserByUserId(userId);
		model.addAttribute("user", user);
		return "home/homePage";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@GetMapping("/information")
	public String information() {
		return "information/information";
	}
}
