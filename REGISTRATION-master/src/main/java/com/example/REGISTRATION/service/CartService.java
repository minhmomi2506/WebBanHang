package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.REGISTRATION.entity.CartItem;
import com.example.REGISTRATION.entity.User;

@Service
@Transactional
public interface CartService {
	List<CartItem> listCartItems(User user);
	
	int countNumberInCarts(User user);

	int addProduct(Long productId, int quantity, User user);

	int updateQuantity(Long productId, int quantity, User user);

	void deleteFromCart(Long productId, User user);

	void checkOut(User user, String howToPay, String address);

}
