package com.example.REGISTRATION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.Category;
import com.example.REGISTRATION.repo.CategoryRepo;
import com.example.REGISTRATION.service.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	/*DELETE CATEGORY*/
	@DeleteMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable Long id) {
			Category category = categoryRepo.findCategoryById(id);
			categoryService.deleteCategory(category.getId());
			return "Xóa hạng mục thành công!";
	}
	
	/*GET ALL CATEGORIES*/
	@GetMapping("/getAllCategories")
	public List<Category> getAll(){
		return categoryService.findAll();
	}
}
