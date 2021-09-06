package com.example.REGISTRATION.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.CartItem;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.UserRepo;
import com.example.REGISTRATION.service.CartService;

//<!-- <div th:if="${totalPages > 1}" style="text-align: center;">
//<div class="row col-sm-10">
//	<div class="col-sm-1">
//		<span th:each="i:${#numbers.sequence(1,totalPages)}"> <a
//			th:if="${currentPage != i}" th:href="@{'/page/'+${i}}">[[${i}]]</a>
//			<span th:unless="${currentPage != i}">[[${i}]]</span> &nbsp;
//			&nbsp;
//		</span>
//	</div>
//	<div class="col-sm-1"></div>
//</div>
//</div> -->
@RestController
public class CartController {
	@Autowired
	private CartService cartService;

	@Autowired
	private UserRepo userRepo;

	/* ADD PRODUCT TO CART */
	@PostMapping("/addProductToCart/{pid}/{qty}")
	public String addToCart(@PathVariable("pid") Long id, @PathVariable("qty") int quantity, Model model,
			Principal principal) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		int addedQuantity = cartService.addProduct(id, quantity, user);
		model.addAttribute("user", user);
		return addedQuantity + "";
	}

	/* UPDATE QUANTITY */
	@PostMapping("/updateQuantity/{pid}/{qty}")
	public String updateQuantity(@PathVariable("pid") Long id, @PathVariable("qty") int quantity, Principal principal) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		int subTotal = cartService.updateQuantity(id, quantity, user);
		return subTotal + "";
	}

	/* DELETE FROM CART */
	@DeleteMapping("/deleteFromCart/{productId}")
	public void deleteFromCart(@PathVariable Long productId, Principal principal) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		cartService.deleteFromCart(productId, user);
	}
	
	/*GET ALL*/
	@GetMapping("/getAllFromCart")
	public List<CartItem> getAllFromCart(Principal principal){
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		return cartService.listCartItems(user);
	}

}
