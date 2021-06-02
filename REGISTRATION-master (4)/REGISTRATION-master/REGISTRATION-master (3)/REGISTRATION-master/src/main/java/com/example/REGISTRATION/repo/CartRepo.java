package com.example.REGISTRATION.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.REGISTRATION.entity.CartItem;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.entity.User;

@Repository
@Transactional
public interface CartRepo extends JpaRepository<CartItem, Long> {
	public List<CartItem> findByUser(User user);
	
	public CartItem findCartItemById(Long id);
	
	public CartItem findByUserAndProduct(User user , Product product);
	
	@Query("UPDATE CartItem c set quantity = ?1 WHERE c.product.id = ?2 AND c.user.id = ?3")
	@Modifying
	void updateQuantity(int quantity, Long productId , Long userId);
	
	@Query("delete from CartItem c where c.user.id = ?1 ")
	@Modifying
	void deleteByUser(Long userId);
	
}
