package com.example.REGISTRATION.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "bill")
@Data
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column
	private String howToPay;
	
	@Column
	private int toTal;
	
	@Column
	private Date buyDate;
	
	@Column
	private String address;
	
	@Column
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;
}
