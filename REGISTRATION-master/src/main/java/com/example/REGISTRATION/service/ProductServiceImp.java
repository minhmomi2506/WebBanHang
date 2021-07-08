package com.example.REGISTRATION.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.REGISTRATION.entity.Category;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.repo.CategoryRepo;
import com.example.REGISTRATION.repo.ProductRepo;

@Component
@Transactional
public class ProductServiceImp implements ProductService {
//	private String TABLE_NAME = "PRODUCT";
//
//	private HashOperations<String, Long, Product> hashOperations;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	/* ADD PRODUCT */
	@Override
	public void saveProductToDB(Product product, MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			if (file != null) {
				product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		hashOperations.put(TABLE_NAME, product.getId(), product);
		productRepo.save(product);
	}

	/* LIST PRODUCT */
	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

//	@Override
//	public Map<Long, Product> findAll() {
//		return hashOperations.entries(TABLE_NAME);
//	}

//	/* XOA */
	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteProductById(id);
	}

//
//	/* EDIT */

	@Override
	public void editProductImage(Long id, MultipartFile file) {
		Product product = productRepo.findProductById(id);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			System.out.println("not a a valid file");
		}
		try {
			if (file != null) {
				product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		productRepo.save(product);
	}

	@Override
	public void editProduct(Long id, String name, String description, int price, int number, String categoryName) {
		Product product = productRepo.findProductById(id);
		Category category = categoryRepo.findByCategoryName(categoryName);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setNumber(number);
		product.setCategory(category);
		productRepo.save(product);
	}

	@Override
	public Product findProductById(Long id) {
		return productRepo.findProductById(id);
	}

	/* TIM KIEM */
	@SuppressWarnings("unused")
	@Override
	public List<Product> productSearch(String name, int minp, int maxp) {
		List<Product> products = productRepo.findAll();
		List<Product> productSearch = new ArrayList<Product>();
		for (Product product : products) {
		
					if (name != null && product.getName().toLowerCase().contains(name.toLowerCase())) {						
						if (product.getPrice() >= minp && product.getPrice() <= maxp) {
							productSearch.add(product);
							break;
						}
				break;
			}

		}
		return productSearch;
	}

	@Override
	public List<Product> groupBy(String categoryName) {
		// TODO Auto-generated method stub
		List<Product> products = productRepo.findAll();
		List<Product> productSearch = new ArrayList<Product>();
		for (Product product : products) {
			if (product.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
				productSearch.add(product);
			}
		}
		return productSearch;
	}
}
