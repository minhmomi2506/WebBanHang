package com.example.Minh.WebSpringBoot.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false , unique = true)
	private String userId;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phoneNumber;
	
	@Column(nullable = false)
	private String address;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;

}
