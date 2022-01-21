package com.example.REGISTRATION.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	Product findProductById(Long id);
	
	List<Product> findByCategory(String category);
	
	@Query("delete from Product where id = ?1")
	@Transactional
	@Modifying
	void deleteProductById(Long id);
	
	Product findProductByName(String name);
	
	List<Product> findByNameContainingAndPriceBetween(String name, int minp, int maxp);
}
