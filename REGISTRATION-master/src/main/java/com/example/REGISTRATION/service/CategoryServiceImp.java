package com.example.REGISTRATION.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.REGISTRATION.entity.Category;
import com.example.REGISTRATION.repo.CategoryRepo;

@Component
@Transactional
public class CategoryServiceImp implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	
	/*LIST CATEGORIES*/
	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}
	
	/*INSERT CATEGORY*/
	@Override
	public Category insertCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.save(category);
	}
	
	/*DELETE CATEGORY*/
	@Override
	public void deleteCategory(Long id) {
		// TODO Auto-generated method stub
		categoryRepo.deleteCategoryById(id);
	}
}
