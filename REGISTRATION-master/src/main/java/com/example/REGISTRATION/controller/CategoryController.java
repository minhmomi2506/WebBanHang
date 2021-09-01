package com.example.REGISTRATION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
//	/*INSERT CATEGORY*/
	@PostMapping("/addCategory")
	public ResponseEntity<Category> insertCategory(@RequestBody Category category) {
		category = categoryService.insertCategory(category);
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
	
	/*DELETE CATEGORY*/
	@DeleteMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable Long id) {
			Category category = categoryRepo.findCategoryById(id);
			categoryService.deleteCategory(category.getId());
			return "Xóa hạng mục thành công!";
	}
	
	@GetMapping("/getAllCategories")
	public List<Category> getAll(){
		List<Category> categories = categoryRepo.findAll();
		return categories;
	}
}
