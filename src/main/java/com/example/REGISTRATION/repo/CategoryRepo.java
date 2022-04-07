package com.example.REGISTRATION.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	Category findByCategoryName(String categoryName);
	
	Category findCategoryById(Long id);
	
	@Query("delete from Category where id = ?1")
	@Transactional
	@Modifying
	void deleteCategoryById(Long id);
}
