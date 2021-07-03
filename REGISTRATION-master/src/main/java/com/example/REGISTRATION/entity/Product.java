package com.example.REGISTRATION.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = 1L;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private int price;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column
	private int number;

}
