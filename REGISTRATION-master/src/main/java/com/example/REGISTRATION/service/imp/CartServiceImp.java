package com.example.REGISTRATION.service.imp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.CartItem;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.entity.Status;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.BillRepo;
import com.example.REGISTRATION.repo.CartRepo;
import com.example.REGISTRATION.repo.ProductRepo;
import com.example.REGISTRATION.repo.StatusRepo;
import com.example.REGISTRATION.service.CartService;

@Component
@Transactional
public class CartServiceImp implements CartService {
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private BillRepo billRepo;

	@Autowired
	private StatusRepo statusRepo;

	@Override
	public List<CartItem> listCartItems(User user) {
		List<CartItem> cartItems = cartRepo.findByUser(user);
		List<CartItem> productsInCart = new ArrayList<CartItem>();
		for (CartItem cartItem : cartItems) {
			productsInCart.add(cartItem);
		}
		return productsInCart;
	}
	
	@Override
	public int countNumberInCarts(User user) {
		List<CartItem> cartItems = cartRepo.findByUser(user);
		int count = 0;
		for (CartItem cartItem : cartItems) {
			count += 1;
		}
		return count;
	}
	/* CHECK OUT */
	@Override
	public void checkOut(User user, String howToPay, String address) {
		// TODO Auto-generated method stub
		List<CartItem> cartItems = cartRepo.findByUser(user);
		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		for (CartItem cartItem : cartItems) {
			Bill bill = new Bill();
			bill.setBuyDate(date);
			bill.setHowToPay(howToPay);
			bill.setAddress(address);
			bill.setQuantity(cartItem.getQuantity());
			bill.setUser(cartItem.getUser());
			bill.setProductPrice(cartItem.getProduct().getPrice());
			bill.setProduct(cartItem.getProduct());
			bill.setPhoneNumber(user.getPhoneNumber());
			Product product = cartItem.getProduct();
			product.setNumber(product.getNumber() - cartItem.getQuantity());
			bill.setToTal(bill.getQuantity() * cartItem.getProduct().getPrice());
			Status status = statusRepo.findStatusByStatusName("Đang xử lý");
			bill.setStatus(status);
			productRepo.save(product);
			billRepo.save(bill);
		}
		cartRepo.deleteByUser(user.getId());
	}

	/* ADD PRODUCT TO CART */
	@Override
	public int addProduct(Long productId, int quantity, User user) {
		int addedQuantity = quantity;
		Product product = productRepo.findProductById(productId);
		CartItem cartItem = cartRepo.findByUserAndProduct(user, product);
		if (cartItem != null) {
			addedQuantity = cartItem.getQuantity() + quantity;
			if (addedQuantity > product.getNumber()) {
				cartItem.setQuantity(product.getNumber());
			} else {
				cartItem.setQuantity(addedQuantity);
			}
		} else {
			cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setUser(user);
			cartItem.setProduct(product);
		}
		cartRepo.save(cartItem);
		return addedQuantity;
	}

	/* UPDATE QUANTITY */
	@Override
	public int updateQuantity(Long productId, int quantity, User user) {
		cartRepo.updateQuantity(quantity, productId, user.getId());
		Product product = productRepo.findProductById(productId);
		int subTotal = product.getPrice() * quantity;
		return subTotal;
	}

	/* DELETE FROM CART */
	@Override
	public void deleteFromCart(Long productId, User user) {
		Product product = productRepo.findProductById(productId);
		CartItem cartItem = cartRepo.findByUserAndProduct(user, product);
		cartRepo.deleteById(cartItem.getId());
	}
}
