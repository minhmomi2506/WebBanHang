package com.example.REGISTRATION.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.repo.ProductRepo;
import com.example.REGISTRATION.service.ProductService;

@Component
@Transactional
public class ProductServiceImp implements ProductService {
//	private String TABLE_NAME = "PRODUCT";
//
//	private HashOperations<String, Long, Product> hashOperations;

	@Autowired
	private ProductRepo productRepo;

	/* ADD PRODUCT */
	@Override
	public Product saveProductToDB(Product product, MultipartFile file) {
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
		return product;
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

	/* XOA */
	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteProductById(id);
	}

	/* EDIT */
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
	public Product editProduct(Long id, Product product) {
		Product product1 = productRepo.findProductById(id);
		product1.setName(product.getName());
		product1.setNumber(product.getNumber());
		product1.setPrice(product.getPrice());
		product1.setDescription(product.getDescription());
		product1.setCategory(product.getCategory());
		productRepo.save(product1);
		return product1;
	}

	@Override
	public Product findProductByName(String productName) {
		return productRepo.findProductByName(productName);
	}

	/* TIM KIEM */
	@Override
	public List<Product> productSearch(String name, int minp, int maxp) {
		return productRepo.findByNameContainingAndPriceBetween(name, minp, maxp);
	}

	/* THONG KE */
	List<Product> groupBy(String categoryName) {
		List<Product> products = productRepo.findAll();
		List<Product> productSearch = new ArrayList<Product>();
		for (Product product : products) {
			if (product.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
				productSearch.add(product);
			}
		}
		return productSearch;
	}

	/* THONG KE TAI NGHE */
	@Override
	public List<Product> cars() {
		List<Product> products = groupBy("Ô tô");
		return products;
	}

	/* THONG KE DIEN THOAI */
	@Override
	public List<Product> planes() {
		List<Product> products = groupBy("Máy bay");
		return products;
	}

	/* THONG KE LAPTOP */
	@Override
	public List<Product> minifigures() {
		List<Product> products = groupBy("Minifigure");
		return products;
	}
	
	/* THONG KE LAPTOP */
	@Override
	public List<Product> ships() {
		List<Product> products = groupBy("Tàu");
		return products;
	}
}
