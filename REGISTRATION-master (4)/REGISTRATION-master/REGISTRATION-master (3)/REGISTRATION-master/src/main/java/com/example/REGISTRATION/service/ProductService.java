package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.REGISTRATION.entity.Product;

@Service
public interface ProductService {
	public void saveProductToDB(Product product , MultipartFile file);

	public List<Product> getAllProduct();
//	
////	Map<Long, Product> findAll();
//	
	void deleteProduct(Long id);
	
	void editProductImage(Long id, MultipartFile file) ;
//	
	void editProduct(Long id , String name , String description , int price ,int number , String category);
	
	Product findProductById(Long id);
	
	List<Product> productSearch(String name , int minp , int maxp);
	
	List<Product> earPhone();
	
//	List<Product> earPhone(Pageable pageable);
	
	List<Product> earPhoneKhongDay();
	
	Page<Product> findPaginated(int pageNum , int pageSize);
	
	List<Product> earPhoneBluetooth();
	
	List<Product> mobilePhone();
	
	List<Product> mobilePhoneSamsung();
	
	List<Product> iPhone();
	
	List<Product> mobilePhoneXiaomi();
	
	List<Product> mobilePhoneHuawei();
	
	List<Product> laptopASUS();
	
	List<Product> laptopDell();
	
	List<Product> laptopApple();
	
	List<Product> laptopLenovo();
	
	List<Product> laptopHp();
	
	List<Product> laptop();
}
