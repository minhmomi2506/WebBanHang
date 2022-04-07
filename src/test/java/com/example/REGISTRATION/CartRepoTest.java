package com.example.REGISTRATION;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.REGISTRATION.entity.CartItem;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.CartRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CartRepoTest {
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testAddOneCartItem() {
		Product product = entityManager.find(Product.class, 204L);
		User user = entityManager.find(User.class, 10L);
		CartItem cartItem = new CartItem();
		cartItem.setUser(user);
		cartItem.setProduct(product);
		cartItem.setQuantity(2);
		CartItem newItem = cartRepo.save(cartItem);
		assertTrue(newItem.getId() > 0);
		
	}
	
//	@Test
//	public void testGetCartItemsByUser() {
//		User user = new User();
//		user.setId(1L);
//		
//		List<CartItem> cartItems = cartRepo.findByUser(user);
//		assertEquals(1, cartItems.size());
//	}
}
