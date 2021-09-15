package com.example.REGISTRATION.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.Category;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.entity.Status;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.CategoryRepo;
import com.example.REGISTRATION.repo.StatusRepo;
import com.example.REGISTRATION.repo.UserRepo;
import com.example.REGISTRATION.service.BillService;
import com.example.REGISTRATION.service.CategoryService;
import com.example.REGISTRATION.service.ProductService;
import com.example.REGISTRATION.service.UserService;

@Controller
@RequestMapping("/admin/**")
public class AdminController {
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private BillService billService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private StatusRepo statusRepo;

	/* INSERT PRODUCT */
	@PostMapping("/addP")
	public String saveProduct(Product product, MultipartFile productImage) {
//		rabbittemplate.convertAndSend(ProductConfigReceive.EXCHANGE_NAME1, ProductConfigReceive.ROUTING_KEY1, product);
		productService.saveProductToDB(product, productImage);
		return "redirect:/admin";
	}

	/* EDIT PRODUCT IMAGE */
	@PostMapping("/editProductImage")
	public String editProductImage(Long id, MultipartFile file) {
		productService.editProductImage(id, file);
		return "redirect:/admin";
	}

	/* STATISTICS */
	@RequestMapping("/statistic")
	public String statistic(Principal principal, Model model) {
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
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

		}
		return "admin/product/searchProduct";
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
		}
		return "admin/groupBy/earPhone";
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
		}
		return "admin/groupBy/mobilePhone";
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
		}
		return "admin/groupBy/lap";
	}

	/* HOME PAGE */
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products", products);
		String username = principal.getName();
		User user = userRepo.findUserByUsername(username);
		model.addAttribute("user", user);
		if (user.getUser_role().equalsIgnoreCase("admin")) {
			model.addAttribute("role", user.getUser_role());
		}
		return "admin/other/homePage";
	}

	/* EDIT USER IMAGE */
	@PostMapping("/editUserImage")
	public String editUserImage(User user, MultipartFile file, Principal principal) {
		String username = principal.getName();
		user = userRepo.findUserByUsername(username);
		userService.editUserImage(file, user);
		return "redirect:/admin";
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
		}
		return "admin/bill/bill";
	}
}
