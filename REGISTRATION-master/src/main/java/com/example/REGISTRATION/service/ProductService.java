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
	
	Product findProductById(Long id);
	
	List<Product> productSearch(String name , int minp , int maxp);
	
	List<Product> earPhone();
	
//	List<Product> earPhoneKhongDay();
//	
//	List<Product> earPhoneBluetooth();
//	
	List<Product> mobilePhone();
//	
//	List<Product> mobilePhoneSamsung();
//	
//	List<Product> iPhone();
//	
//	List<Product> mobilePhoneXiaomi();
//	
//	List<Product> mobilePhoneHuawei();
//	
//	List<Product> laptopASUS();
//	
//	List<Product> laptopDell();
//	
//	List<Product> laptopApple();
//	
//	List<Product> laptopLenovo();
//	
//	List<Product> laptopHp();
//	
	List<Product> laptop();
}
