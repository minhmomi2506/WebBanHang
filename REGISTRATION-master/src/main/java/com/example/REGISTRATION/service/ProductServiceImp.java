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

import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.repo.ProductRepo;

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
	public Product findProductById(Long id) {
		return productRepo.findProductById(id);
	}

	/* TIM KIEM */
	@Override
	public List<Product> productSearch(String name, int minp, int maxp) {
		List<Product> products = productRepo.findAll();
		List<Product> productSearch = new ArrayList<Product>();
		for (Product product : products) {
			if (name != null && product.getName().toLowerCase().contains(name.toLowerCase())) {
				if (product.getPrice() >= minp && product.getPrice() <= maxp) {
					productSearch.add(product);
				}

			}

		}
		return productSearch;
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
	public List<Product> earPhone() {
		List<Product> products = groupBy("Tai nghe");
		return products;
	}

	/* THONG KE DIEN THOAI */
	@Override
	public List<Product> mobilePhone() {
		List<Product> products = groupBy("Điện thoại");
		return products;
	}

	/* THONG KE LAPTOP */
	@Override
	public List<Product> laptop() {
		List<Product> products = groupBy("Laptop");
		return products;
	}

//	@Override
//	public List<Product> earPhoneKhongDay() {
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Tai nghe")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Không dây")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE TAI NGHE BLUETOOTH */
//	@Override
//	public List<Product> earPhoneBluetooth() {
//		// TODO Auto-generated method stub
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			int dem = 0;
//			for (int i = 0; i <= product.getName().length(); i++) {
//				if (dem == 1)
//					break;
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Tai nghe")) {
//						if (product.getName().substring(i, j).equalsIgnoreCase("Bluetooth")
//								|| product.getName().substring(i, j).equalsIgnoreCase("Không dây")) {
//							productSearch.add(product);
//							dem = 1;
//							break;
//						}
//					}
//				}
//			}
//
//		}
//		return productSearch;
//
//	}
//
//
//	/* THONG KE DIEN THOAI SAMSUNG */
//	@Override
//	public List<Product> mobilePhoneSamsung() {
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Điện thoại")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Samsung")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE DIEN THOAI IPHONE */
//	@Override
//	public List<Product> iPhone() {
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Điện thoại")
//							&& product.getName().substring(i, j).equalsIgnoreCase("iphone")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE DIEN THOAI XIAOMI */
//	public List<Product> mobilePhoneXiaomi() {
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Điện thoại")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Xiaomi")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE DIEN THOAI HUAWEI */
//	public List<Product> mobilePhoneHuawei() {
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Điện thoại")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Huawei")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//
//	/* THONG KE LAPTOP ASUS */
//	@Override
//	public List<Product> laptopASUS() {
//		// TODO Auto-generated method stub
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Laptop")
//							&& product.getName().substring(i, j).equalsIgnoreCase("ASUS")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE LAPTOP DELL */
//	@Override
//	public List<Product> laptopDell() {
//		// TODO Auto-generated method stub
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Laptop")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Dell")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE LAPTOP APPLE */
//	@Override
//	public List<Product> laptopApple() {
//		// TODO Auto-generated method stub
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Laptop")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Apple")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE LAPTOP LENOVO */
//	@Override
//	public List<Product> laptopLenovo() {
//		// TODO Auto-generated method stub
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Laptop")
//							&& product.getName().substring(i, j).equalsIgnoreCase("Lenovo")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
//
//	/* THONG KE LAPTOP HP */
//	@Override
//	public List<Product> laptopHp() {
//		// TODO Auto-generated method stub
//		List<Product> products = productRepo.findAll();
//		List<Product> productSearch = new ArrayList<Product>();
//		for (Product product : products) {
//			for (int i = 0; i <= product.getName().length(); i++) {
//				for (int j = i; j <= product.getName().length(); j++) {
//					if (product.getCategory().equalsIgnoreCase("Laptop")
//							&& product.getName().substring(i, j).equalsIgnoreCase("hp")) {
//						productSearch.add(product);
//					}
//				}
//			}
//		}
//		return productSearch;
//	}
}
