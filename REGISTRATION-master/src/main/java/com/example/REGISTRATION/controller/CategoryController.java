package com.example.REGISTRATION.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@RequestMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable Long id) {
		Category category = categoryRepo.findCategoryById(id);
		categoryService.deleteCategory(category.getId());
		return "redirect:/listCategories";
	}
}
