package com.example.REGISTRATION.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.repo.ProductRepo;
import com.example.REGISTRATION.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductService productService;
	
	/*DELETE PRODUCT*/
	@PostMapping("/deleteProductById/{id}")
	public void deleteProduct(@PathVariable Long id) {
		Product product = productRepo.findProductById(id);
		productService.deleteProduct(product.getId());
	}
}
