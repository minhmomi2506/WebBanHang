package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.REGISTRATION.entity.Category;
import com.example.REGISTRATION.repo.CategoryRepo;

@Component
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
	public void insertCategory(Category category) {
		// TODO Auto-generated method stub
		categoryRepo.save(category);
	}
	
	/*DELETE CATEGORY*/
	@Override
	public void deleteCategory(Long id) {
		// TODO Auto-generated method stub
		categoryRepo.deleteById(id);
	}
}
