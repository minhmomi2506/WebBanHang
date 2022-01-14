package com.example.REGISTRATION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/* INSERT PRODUCT */
	
	/*GET ALL PRODUCTS*/
	@GetMapping("/getAllProducts")
	public List<Product> getAllProducts(){
		return productService.getAllProduct();
	}
	
	/*EDIT PRODUCT INFO*/
	@PutMapping("/editProductInfo/{id}")
	public ResponseEntity<Product> editProductInfo(@PathVariable Long id, @RequestBody Product product) {
		Product product1 = productService.editProduct(id, product);
		return new ResponseEntity<Product>(product1, HttpStatus.OK);
	}

	/* DELETE PRODUCT */
	@DeleteMapping("/deleteProductById/{id}")
	public void deleteProduct(@PathVariable Long id) {
		Product product = productRepo.findProductById(id);
		productService.deleteProduct(product.getId());
	}
}
