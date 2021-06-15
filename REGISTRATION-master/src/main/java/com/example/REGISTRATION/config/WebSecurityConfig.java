package com.example.REGISTRATION.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.REGISTRATION.oauth.CustomOAuth2UserService;
import com.example.REGISTRATION.oauth.OAuth2LoginSuccessHandler;
import com.example.REGISTRATION.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private DataSource dataSource;
	@Autowired
	private CustomOAuth2UserService oauth2UserService;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/register","/save","/oauth2/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().permitAll().loginPage("/login")
		.usernameParameter("email")
		.passwordParameter("password")
		.defaultSuccessUrl("/")
		.successHandler(authSuccessHandler)
		.and()
		.oauth2Login()
			.loginPage("/login")
			.userInfoEndpoint().userService(oauth2UserService)
			.and()
			.successHandler(oauth2LoginSuccessHandler)
		.and()
		.logout().permitAll()
		.and().logout().logoutSuccessUrl("/login").logoutUrl("/logout").permitAll();

	}
	
	@Autowired
	private OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;
	
	@Autowired
	private CustomAuthenticationSuccessHandler authSuccessHandler;
	
}
