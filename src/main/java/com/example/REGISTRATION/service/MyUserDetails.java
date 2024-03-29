package com.example.REGISTRATION.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.REGISTRATION.entity.Role;
import com.example.REGISTRATION.entity.User;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;
	
	public MyUserDetails(User user) {
		this.user = user;
	}
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Role role = user.getRole();
    	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
    	return authorities;
    }
	
    public Long getID() {
    	return user.getId();
    }
    
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}

//registration:
//    depends_on:
//      - mysqldb
//    container_name: registration
//    image: registration
//    restart: always
//    environment:
//      SPRING_DATASOURCE_URL: jdbc:mysql://mysqldb:3306/registration?autoReconnect=true&useSSL=false
//    build: .
//    ports:
//      - 8080:8080  
