package com.example.REGISTRATION.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.REGISTRATION.loginWithSocial.AuthenProvider;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column
	private String password;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	private boolean enabled = true;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider")
	private AuthenProvider authProvider;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
}