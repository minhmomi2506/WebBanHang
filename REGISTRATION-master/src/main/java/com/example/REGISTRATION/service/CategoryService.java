package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.REGISTRATION.entity.Category;

@Service
public interface CategoryService {
	List<Category> findAll();
	
	void insertCategory(Category category);
	
	void deleteCategory(Long id);
}
