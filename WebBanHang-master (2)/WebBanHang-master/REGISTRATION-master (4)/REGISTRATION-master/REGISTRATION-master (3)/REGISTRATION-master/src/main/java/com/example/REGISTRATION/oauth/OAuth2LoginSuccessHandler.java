package com.example.REGISTRATION.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.loginWithSocial.AuthenProvider;
import com.example.REGISTRATION.service.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
		String email = oauth2User.getEmail();
		User user = userService.findUserByEmail(email);
		String name = oauth2User.getFullName();
		if (user == null) {
			userService.registerNewUserAfterOAuthLoginSuccess(email, name, AuthenProvider.SOCIAL);
		} else {
			userService.updateExistUserAfterOAuthLoginSuccess(user, name, AuthenProvider.SOCIAL);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
