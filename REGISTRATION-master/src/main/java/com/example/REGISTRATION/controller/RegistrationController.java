package com.example.REGISTRATION.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.CartItem;
import com.example.REGISTRATION.entity.Category;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.entity.Status;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.CartRepo;
import com.example.REGISTRATION.repo.CategoryRepo;
import com.example.REGISTRATION.repo.ProductRepo;
import com.example.REGISTRATION.repo.StatusRepo;
import com.example.REGISTRATION.repo.UserRepo;
import com.example.REGISTRATION.service.BillService;
import com.example.REGISTRATION.service.CartService;
import com.example.REGISTRATION.service.CategoryService;
import com.example.REGISTRATION.service.ProductService;
import com.example.REGISTRATION.service.UserService;

@Controller
public class RegistrationController {
	@Autowired
	private CategoryRepo categoryRepo;

	static List<String> howToPays = new ArrayList<String>();
	static {
		howToPays.add("Thanh toán khi nhận hàng");
	}

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartService cartService;

	@Autowired
	private BillService billService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private StatusRepo statusRepo;

//	@Autowired
//	private RabbitTemplate rabbittemplate;

	/* ADMIN ACTION */

	/* INSERT PRODUCT */
	@PostMapping("/addP")
	public String saveProduct(Product product, MultipartFile productImage) {
//		rabbittemplate.convertAndSend(ProductConfigReceive.EXCHANGE_NAME1, ProductConfigReceive.ROUTING_KEY1, product);
		productService.saveProductToDB(product, productImage);
		return "redirect:/";
	}

	/* EDIT PRODUCT IMAGE */
	@PostMapping("/editProductImage")
	public String editProductImage(Long id, MultipartFile file) {
		productService.editProductImage(id, file);
		return "redirect:/";
	}

	/* STATISTICS */
	@RequestMapping("/statistic")
	public String statistic(Principal principal, Model model) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		return "admin/statistic/statistic";
	}

	/* LIST USERS */
	@RequestMapping("/list_users")
	public String list(Model model, HttpSession session, Principal principal) {
		List<User> users1 = userRepo.findAll();
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		List<User> users = new ArrayList<User>();
		for (User user1 : users1) {
			if (user1 != user) {
				users.add(user1);
			}
		}
		model.addAttribute("users", users);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "admin/user/list_users";
	}

	/* LIST CATEGORIES */
	@GetMapping("/listCategories")
	public String listCategories(Principal principal, Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "admin/category/listCategories";
	}

	/* BUY PRODUCT */
	@GetMapping("/buyProduct/{id}")
	public String infoProduct(@PathVariable Long id, Model model, Principal principal) {
		Product product = productRepo.findProductById(id);
		model.addAttribute("product", product);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		List<CartItem> cartItems = cartRepo.findByUser(user);
		Product product1 = productRepo.findProductById(id);
		for (CartItem cartItem : cartItems) {
			if (cartItem.getProduct() == product1) {
				model.addAttribute("numberInCart", cartItem.getQuantity());
			}
		}
		return "customer/product/buyProduct";
	}

	/* ADMIN + USER ACTION */

	/* SEARCH PRODUCT */
	@RequestMapping("/searchProduct")
	public String search(@RequestParam("productName") String name, @RequestParam int minp, @RequestParam int maxp,
			Model model, Principal principal) {
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		List<Product> products = productService.productSearch(name, minp, maxp);
		model.addAttribute("products", products);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
			return "admin/product/searchProduct";
		}
		return "customer/product/searchProductCustomer";
	}

	/* TAI NGHE */
	@GetMapping("/taiNghe")
	public String taiNghe(Model model, Principal principal) {
		List<Product> earPhones = productService.earPhone();
		model.addAttribute("products", earPhones);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
			return "admin/groupBy/earPhone";
		} else {
			return "customer/groupBy/earPhoneCustomer";
		}
	}

	/* DIEN THOAI */
	@GetMapping("/dienThoai")
	public String dienThoai(Model model, Principal principal) {
		List<Product> mobilePhones = productService.mobilePhone();
		model.addAttribute("products", mobilePhones);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
			return "admin/groupBy/mobilePhone";
		} else {
			return "customer/groupBy/mobilePhoneCustomer";
		}
	}

	/* LAPTOP */
	@GetMapping("/laptop")
	public String laptop(Model model, Principal principal) {
		List<Product> mobilePhones = productService.laptop();
		model.addAttribute("products", mobilePhones);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
			return "admin/groupBy/lap";
		}
		return "customer/groupBy/lapCustomer";
	}

	/* LOG IN */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request, Model model) {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen == null || authen instanceof AnonymousAuthenticationToken || request.getSession() == null) {
			return "login/loginPage";
		}
		model.addAttribute("user", user);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", user);
		return "redirect:/";
	}

	/* LOG OUT */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	/* REGISTER */
	@RequestMapping("/register")
	public String aaa() {
		return "login/register";
	}

	/* HOME PAGE */
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products", products);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
			return "admin/other/homePage";
		} else {
			return "customer/other/homePageCustomer";
		}
	}

	/* SIGN UP */
	@PostMapping("/save")
	public String save(MultipartFile file, User user) {
		userService.saveUserToDB(file, user);
		return "redirect:/login";
	}

	/* EDIT USER IMAGE */
	@PostMapping("/editUserImage")
	public String editUserImage(User user, MultipartFile file, Principal principal) {
		String username = principal.getName();
		user = userRepo.findUserByUsername(username);
		userService.editUserImage(file, user);
		return "redirect:/";
	}

	/* LIST BILL */
	@GetMapping("/listBill")
	public String listBill(Principal principal, Model model) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		List<Status> statuses1 = statusRepo.findAll();
		List<Status> statuses = new ArrayList<Status>();
		for (Status status : statuses1) {
			if (status.getStatusName().equals("Hủy đơn") == false) {
				statuses.add(status);
			}
		}
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			List<Bill> bills = billService.getAll();
			model.addAttribute("bills", bills);
			model.addAttribute("role", user.getUser_role());
			model.addAttribute("statuses", statuses);
			return "admin/bill/bill";
		} else {
			List<Bill> bills = billService.findByUser(user);
			model.addAttribute("bills", bills);
			return "customer/bill/billCustomer";
		}
	}

	/* USER ACTION */

	/* SHOW CART */
	@GetMapping("/cart")
	public String showShoppingCart(Model model, Principal principal) {
		model.addAttribute("howToPays", howToPays);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		List<CartItem> cartItems = cartService.listCartItems(user);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "customer/cart/shopping_cart";

	}

	/* MAP */
	@GetMapping("/map")
	public String map() {
		return "map";
	}

	/* BUY PRODUCT FROM CART */
	@PostMapping("/checkOut")
	public String checkOut(String howToPay, Principal principal) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		cartService.checkOut(user, howToPay);
		return "redirect:/cart";
	}
}
