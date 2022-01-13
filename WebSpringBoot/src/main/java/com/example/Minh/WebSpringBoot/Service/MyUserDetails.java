package com.example.Minh.WebSpringBoot.Service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Minh.WebSpringBoot.Entity.User;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;
	
	public MyUserDetails(User user) {
		this.user = user;
	}
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return null;
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
		return user.getUserId();
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
