package com.example.REGISTRATION.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.rabbitConfig.ProductConfigReceive;
import com.example.REGISTRATION.repo.CategoryRepo;
import com.example.REGISTRATION.repo.ProductRepo;
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
		howToPays.add("Thanh toán bằng thẻ ngân hàng");
		howToPays.add("Thanh toán bằng ví AirPays");
	}
	
	static List<String> statuses = new ArrayList<String>();
	static {
		statuses.add("Đang xử lý");
		statuses.add("Chờ nhận hàng");
		statuses.add("Đang giao hàng");
		statuses.add("Đã nhận hàng");
	}
	
	@Autowired
	private RabbitTemplate rabbittemplate;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private CategoryService categoryService;

	/* INSERT PRODUCT */

	@GetMapping("/addProduct")
	public String showAddProduct(Model model) {
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "product/AddProduct";
	}

	@PostMapping("/addP")
	public String saveProduct(Product product, MultipartFile file) {
		rabbittemplate.convertAndSend(ProductConfigReceive.EXCHANGE_NAME1, ProductConfigReceive.ROUTING_KEY1, product);
		productService.saveProductToDB(product, file);
		return "redirect:/";
	}

	/* CHANGE PRODUCT INFORMATION */
	
//	@GetMapping("/edit/{id}")
//	public String editProduct(@PathVariable Long id , Model model , Principal principal) {
//		Product product = productRepo.findProductById(id);
//		Category category = product.getCategory();
//		model.addAttribute("category", category);
//		model.addAttribute("product", product);
//		String email = principal.getName();
//		User user = userRepo.findUserByEmail(email);
//		model.addAttribute("user", user);
//		List<Category> categories = categoryRepo.findAll();
//		model.addAttribute("categories", categories);
//		if (user.getUser_role().equalsIgnoreCase("admin")) {
//			model.addAttribute("role", user.getUser_role());
//		}
//		return "product/edit";
//	}

	@PostMapping("/editNewProductImage")
	public String editProductImage(Long id, MultipartFile file){
		productService.editProductImage(id, file);
		return "redirect:/";
	}
	
	@PostMapping("/editProductInfo")
	public String editProductInfo(Long id, String name , String description , int price , int number , String categoryName) {
		productService.editProduct(id, name, description, price, number, categoryName);
		return "redirect:/";
	}
	
	/*DELETE PRODUCT*/
	@GetMapping("/deleteProductById/{id}")
	public String deleteProduct(@PathVariable Long id) {
		Product product = productRepo.findProductById(id);
		productService.deleteProduct(product.getId());
		return "redirect:/";
	}

	/*
	 * BUY PRODUCT
	 */
	@GetMapping("/buyProduct/{id}")
	public String infoProduct(@PathVariable("id") Long id, Model model, Principal principal) {
		Product product = productRepo.findProductById(id);
		model.addAttribute("product", product);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		return "product/buyProduct";
	}

	/* SEARCH PRODUCT */
	@RequestMapping("/searchProduct")
	public String search(@RequestParam("productName") String name, @RequestParam int minp , @RequestParam int maxp , Model model, Principal principal) {
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		List<Product> products = productService.productSearch(name , minp , maxp);
		model.addAttribute("products", products);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "product/searchProduct";
	}

	/*GROUP BY*/
	@GetMapping("/{categoryName}")
	public String groupBy(@PathVariable String categoryName , Model model , Principal principal) {
		List<Product> products = productService.groupBy(categoryName);
		model.addAttribute("products", products);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/"+categoryName;
	}
	
	/* LOG IN */

	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request, Model model) {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen == null || authen instanceof AnonymousAuthenticationToken || request.getSession() == null) {
			return "login/login";
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

	/* HOME PAGE */
	
	@GetMapping("/")
	public String home(Model model , Principal principal) {
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products", products);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "other/homePage";
	}

	/* SIGN UP */
	@PostMapping("/save")
	public String save(MultipartFile file, User user) {
		userService.saveUserToDB(file, user);
		return "redirect:/login";
	}

	/* LIST USERS */

	@RequestMapping("/list_users")
	public String list(Model model, HttpSession session, Principal principal) {
		List<User> users = userRepo.findAll();
		model.addAttribute("users", users);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "user/list_users";
	}

	/* EDIT USER INFORMATION */

	@PostMapping("/editUser")
	public String editUser(User user,Principal principal , String fullName , String phoneNumber, String address) {
		String email = principal.getName();
		user = userRepo.findUserByEmail(email);
		userService.editUserInformation(user, fullName, phoneNumber,address);
		return "redirect:/";
	}
	
	@PostMapping("/editUserPassword")
	public String editUserPassword(User user,Principal principal , String password) {
		String email = principal.getName();
		user = userRepo.findUserByEmail(email);
		userService.editUserPassword(user, password);;
		return "redirect:/";
	}
	
	@PostMapping("/editUserImage")
	public String editUserImage(User user , MultipartFile file , Principal principal) {
		String email = principal.getName();
		user = userRepo.findUserByEmail(email);
		userService.editUserImage(file, user);
		return "redirect:/";
	}

	/*
	 * USER'S INFORMATION
	 */
//	@RequestMapping("/info/{id}")
//	public String info(User user1, @PathVariable Long id, Model model) {
//		User user = userRepo.findUserById(id);
//		model.addAttribute("user", user);
//		if (user.getUser_role().equalsIgnoreCase("admin")) {
//			model.addAttribute("role", user.getUser_role());
//		}
//		return "user/information";
//	}

	/* SHOW CART */
	@GetMapping("/cart")
	public String showShoppingCart(Model model, Principal principal) {
		model.addAttribute("howToPays", howToPays);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		List<CartItem> cartItems = cartService.listCartItems(user);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("user")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "cart/shopping_cart";

	}
	
	/*BUY PRODUCT FROM CART*/
	@PostMapping("/buyProduct")
	public String buyProduct(String howToPay, Principal principal) {
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		cartService.checkOut(user, howToPay);
		return "redirect:/cart";
	}
	
	/*MAP */
	@GetMapping("/map")
	public String map() {
		return "map";
	}
	
	/*LIST BILL*/
	@GetMapping("/listBill")
	public String listBill(Principal principal , Model model) {
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			List<Bill> bills = billService.getAll();
			model.addAttribute("bills", bills);
			model.addAttribute("role", user.getUser_role());
			model.addAttribute("statuses", statuses);
			return "bill/bill";
		}
		else {
			List<Bill> bills = billService.findByUser(user);
			model.addAttribute("bills", bills);
			return "bill/billCustomer";
		}
	}
	
	/*DELETE BILL*/
	@PostMapping("/deleteBill/{id}")
	public String deleteBill(@PathVariable Long id) {
		billService.deleteBill(id);
		return "redirect:/listBill";
	}
	
	/*LIST CATEGORIES*/
	@GetMapping("/listCategories")
	public String listCategories(Principal principal , Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "category/listCategories";
	}
	
	/*INSERT CATEGORY*/
	@PostMapping("/addCategory")
	public String insertCategory(Category category) {
		categoryService.insertCategory(category);
		return "redirect:/listCategories";
	}
	
}
