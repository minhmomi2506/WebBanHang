package com.example.REGISTRATION.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	Category findByCategoryName(String categoryName);
	
	Category findCategoryById(Long id);
}
