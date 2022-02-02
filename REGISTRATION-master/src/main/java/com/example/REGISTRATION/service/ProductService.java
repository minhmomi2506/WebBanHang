package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.REGISTRATION.entity.Product;

@Service
public interface ProductService {
	public Product saveProductToDB(Product product, MultipartFile file);

	public List<Product> getAllProduct();

	void deleteProduct(Long id);
	
	void editProductImage(Long id, MultipartFile file) ;
	
	Product editProduct(Long id , Product product);
	
	Product findProductByName(String productName);
	
	List<Product> productSearch(String name , int minp , int maxp);
	
	List<Product> groupBy(String categoryName);
}
