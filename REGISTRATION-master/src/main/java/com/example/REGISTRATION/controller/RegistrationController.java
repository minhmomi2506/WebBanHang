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
import com.example.REGISTRATION.repo.BillRepo;
import com.example.REGISTRATION.repo.CategoryRepo;
import com.example.REGISTRATION.repo.ProductRepo;
import com.example.REGISTRATION.repo.UserRepo;
import com.example.REGISTRATION.service.BillService;
import com.example.REGISTRATION.service.CartService;
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
	private BillRepo billRepo;
	
	@Autowired
	private BillService billService;

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
	
	@GetMapping("/edit/{id}")
	public String editProduct(@PathVariable Long id , Model model , Principal principal) {
		Product product = productRepo.findProductById(id);
		Category category = product.getCategory();
		model.addAttribute("category", category);
		model.addAttribute("product", product);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "product/edit";
	}

	@PostMapping("/editNewProductImage")
	public String editProductImage(Product product, MultipartFile file){
		productService.editProductImage(product.getId(), file);
		return "redirect:/edit/"+product.getId();
	}
	
	@PostMapping("/editProductInfo")
	public String editProductInfo(Long id, String name , String description , int price , int number , String categoryName) {
		Product product = productRepo.findProductById(id);
		Category category = categoryRepo.findByCategoryName(categoryName);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setNumber(number);
		product.setCategory(category);
		productRepo.save(product);
		return "redirect:/edit/"+product.getId();
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

	/* THONG KE TAI NGHE */
	@GetMapping("/taiNghe")
	public String taiNghe(Model model, Principal principal) {
		List<Product> products = productService.earPhone();
		model.addAttribute("products", products);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		model.addAttribute("user", user);
		return "groupBy/taiNghe";
	}
	
//	@RequestMapping("/search/page/{pageNum}")
//	public String earPaginated(@PathVariable int pageNum , Model model, Principal principal) {
//		int pageSize = 5;
//		Page<Product> page = productService.findPaginated(pageNum, pageSize);
//		List<Product> products = page.getContent();
//		model.addAttribute("currentPage", pageNum);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//		model.addAttribute("products", products);
//		String email = principal.getName();
//		User user = userRepo.findUserByEmail(email);
//		model.addAttribute("user", user);
//		if (user.getUser_role().equalsIgnoreCase("admin")) {
//			model.addAttribute("role", user.getUser_role());
//		}
//		return "groupBy/taiNghe";
//	}
	
	
	
	/*THONG KE TAI NGHE KHONG DAY */
	@GetMapping("/taiNgheKhongDay")
	public String taiNgheKhongDay(Model model, Principal principal) {
		List<Product> taiNgheKhongDays = productService.earPhoneKhongDay();
		model.addAttribute("taiNgheKhongDays", taiNgheKhongDays);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		model.addAttribute("user", user);
		return "groupBy/taiNghe/khongDay";
	}
	
	/*THONG KE TAI NGHE BLUETOOTH */
	@GetMapping("/taiNgheBluetooth")
	public String taiNgheBluetooth(Model model, Principal principal) {
		List<Product> taiNgheBluetooths = productService.earPhoneBluetooth();
		model.addAttribute("taiNgheBluetooths", taiNgheBluetooths);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		model.addAttribute("user", user);
		return "groupBy/taiNghe/bluetooth";
	}

	/* THONG KE DIEN THOAI */
	@GetMapping("/dienThoai")
	public String dienThoai(Model model, Principal principal) {
		List<Product> products = productService.mobilePhone();
		model.addAttribute("products", products);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		model.addAttribute("user", user);
		return "groupBy/dienThoai";
	}

	/* THONG KE DIEN THOAI SAMSUNG */
	@GetMapping("/dienThoaiSamsung")
	public String dienThoaiSamsung(Model model, Principal principal) {
		List<Product> phoneSamsungs = productService.mobilePhoneSamsung();
		model.addAttribute("phoneSamsungs", phoneSamsungs);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		model.addAttribute("user", user);
		return "groupBy/dienThoai/samsung";
	}
	
	/* THONG KE DIEN THOAI IPHONE */
	@GetMapping("/dienThoaiiphone")
	public String dienThoaiiphone(Model model, Principal principal) {
		List<Product> iphones = productService.iPhone();
		model.addAttribute("iphones", iphones);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		model.addAttribute("user", user);
		return "groupBy/dienThoai/iphone";
	}
	
	/* THONG KE DIEN THOAI XIAOMI */
	@GetMapping("/dienThoaiXiaomi")
	public String dienThoaiXiaomi(Model model, Principal principal) {
		List<Product> xiaomies = productService.mobilePhoneXiaomi();
		model.addAttribute("xiaomies", xiaomies);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/dienThoai/xiaomi";
	}
	
	/* THONG KE DIEN THOAI HUAWEI */
	@GetMapping("/dienThoaiHuawei")
	public String dienThoaiHuawei(Model model, Principal principal) {
		List<Product> huaweis = productService.mobilePhoneHuawei();
		model.addAttribute("huaweis", huaweis);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/dienThoai/huawei";
	}

	/* THONG KE LAPTOP */
	@GetMapping("/laptop")
	public String laptop(Model model, Principal principal) {
		List<Product> products = productService.laptop();
		model.addAttribute("products", products);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/laptop";
	}
	
	/* THONG KE LAPTOP APPLE */
	@GetMapping("/laptopApple")
	public String laptopApple(Model model, Principal principal) {
		List<Product> apples = productService.laptopApple();
		model.addAttribute("apples", apples);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/laptop/apple";
	}
	
	/* THONG KE LAPTOP ASUS */
	@GetMapping("/laptopASUS")
	public String laptopASUS(Model model, Principal principal) {
		List<Product> asuses = productService.laptopASUS();
		model.addAttribute("asuses", asuses);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/laptop/asus";
	}
	
	/* THONG KE LAPTOP DELL */
	@GetMapping("/laptopDell")
	public String laptopDell(Model model, Principal principal) {
		List<Product> dells = productService.laptopDell();
		model.addAttribute("dells", dells);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/laptop/dell";
	}
	
	/* THONG KE LAPTOP LENOVO */
	@GetMapping("/laptopLenovo")
	public String laptopLenovo(Model model, Principal principal) {
		List<Product> lenovoes = productService.laptopLenovo();
		model.addAttribute("lenovoes", lenovoes);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/laptop/lenovo";
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

	/* THONG KE LAPTOP HP */
	@GetMapping("/laptopHp")
	public String laptopHp(Model model, Principal principal) {
		List<Product> hps = productService.laptopHp();
		model.addAttribute("hps", hps);
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "groupBy/laptop/hp";
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
	public String editUser(User user,Principal principal , String fullName , String phoneNumber , String password) {
		String email = principal.getName();
		user = userRepo.findUserByEmail(email);
		userService.editUserInformation(user, fullName, phoneNumber,password);
		return "redirect:/info/"+user.getId();
	}
	
	@PostMapping("/editUserImage")
	public String editUserImage(User user , MultipartFile file , Principal principal) {
		String email = principal.getName();
		user = userRepo.findUserByEmail(email);
		userService.editUserImage(file, user);
		return "redirect:/info/"+user.getId();
	}

	/*
	 * USER'S INFORMATION
	 */
	@RequestMapping("/info/{id}")
	public String info(User user1, @PathVariable Long id, Model model) {
		User user = userRepo.findUserById(id);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "user/information";
	}

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
			return "bill/bill";
		}
		else {
			List<Bill> bills = billService.findByUser(user);
			model.addAttribute("bills", bills);
			model.addAttribute("role", user.getUser_role());
			return "bill/billCustomer";
		}
	}
	
	/*EDIT BILL STATUS*/
	@GetMapping("/editBill/{id}")
	public String editBill(@PathVariable Long id , Model model , Principal principal) {
		Bill bill = billRepo.findBillById(id);
		model.addAttribute("bill", bill);
		String email = principal.getName();
		User user = userRepo.findUserByEmail(email);
		model.addAttribute("user", user);
		model.addAttribute("statuses", statuses);
		return "bill/edit";
	}
	
	@PostMapping("/editBillInfo")
	public String editBillInfo(Bill bill, String status) {
		billService.editBill(bill.getId(), status);
		return "redirect:/editBill/"+bill.getId();
	}
	
	/*DELETE BILL*/
	@PostMapping("/deleteBill/{id}")
	public String deleteBill(@PathVariable Long id) {
		billService.deleteBill(id);
		return "redirect:/listBill";
	}
	
}
