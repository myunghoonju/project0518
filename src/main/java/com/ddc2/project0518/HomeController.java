package com.ddc2.project0518;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ddc2.project0518.model.CartInfo;
import com.ddc2.project0518.model.PageNation;
import com.ddc2.project0518.model.ProductCart;
import com.ddc2.project0518.model.ProductRegister;
import com.ddc2.project0518.model.UpdateProductInfoValidator;
import com.ddc2.project0518.model.UpdateUserInfoValidator;
import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.util.FileConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@Inject
	UserBO userBO;
	@Inject
	ProductBO productBO;
	@Inject
	FileConfig fileconfig;
	@Inject
	BCryptPasswordEncoder pw_encoder;

	@GetMapping(value = { "/", "/index" })
	public String StartGet(Model model, HttpSession session,PageNation page) {
		
		PageNation pageInit = new PageNation();
		pageInit.setPage(page.getPage());	
		List<ProductRegister> ProductList = productBO.getProductList(pageInit);
		model.addAttribute("ProductList", ProductList);
		
		pageInit.setTotal_count(productBO.count_list(pageInit));
		model.addAttribute("page", pageInit);
			
		return "index";
	}

	@GetMapping("/users/signin")
	public void SigninGet() {
	}

	@GetMapping("/users/signout")
	public void SignoutGet() {
	}

	@PostMapping("/signinProcess")
	public String SigninPost(HttpSession session, UserRegister signinInfo, HttpServletResponse res) {
		String URL = "";
		if (session.getAttribute("signin") != null)
			session.removeAttribute("signin");

		UserRegister result = userBO.signin(signinInfo);

		if (result != null) {
			boolean match = pw_encoder.matches(signinInfo.getPassword(), result.getPassword());

			if (match) {
				session.setAttribute("signin", result);
				session.setAttribute("auth", result.getAuth());
				URL = "redirect:/";
			} else {
				URL = "redirect:/";
			}
		} else {
			URL = "redirect:/users/signin";
		}
		return URL;

	}

	@PostMapping("/userJoin")
	public @ResponseBody String userJoin(@Valid @RequestBody UserRegister userinfo, Errors errors) {
		
		boolean result = false;

		if (errors.hasErrors()) {
			log.info("[관리자메세지]: 회원가입 입력 값이 누락되거나 형식에 맞지 않습니다.");
			return "falied";
		} else {
			String enpwd = pw_encoder.encode(userinfo.getPassword());
			userinfo.setPassword(enpwd);
			result = userBO.insertUser(userinfo);
		}

		if (result) {
			return "success";
		} else {
			return "failed";
		}

	}

	@GetMapping("/signOut")
	public String SignOutGet(HttpSession session, HttpServletRequest req, HttpServletResponse res) {
		Object obj = session.getAttribute("signin");

		if (obj != null) {
			session.removeAttribute("signin");
			session.invalidate();
			return "redirect:/";
		}

		return "redirect:/users/signin";
	}

	@GetMapping("/ViewProduct")
	public String ViewProduct(@RequestParam int product_no, HttpServletRequest req, HttpServletResponse res,
			Model model) {

		HttpSession session = req.getSession();
		ProductRegister productDetail = productBO.getProductDetail(product_no);

		if (productDetail != null) {
			model.addAttribute("productDetail", productDetail);

			ArrayList<ProductRegister> prevWathedList;
			prevWathedList = (ArrayList<ProductRegister>) session.getAttribute("prevWatchedList");
			prevWathedList = productBO.prevWatched(product_no, productDetail, prevWathedList);

			session.setAttribute("prevWatchedList", prevWathedList);
			session.setMaxInactiveInterval(300);
		} else {
			log.info("[관리자메세지]: 존재하지 않는 상품입니다.");
			return "redirect:/";
		}

		return "/ViewProduct";
	}

	@PostMapping("/addCart")
	public @ResponseBody String addCartPost(@RequestParam("product_no") int product_no,
											@RequestParam("amount") int amount,
											HttpServletRequest req,
											HttpServletResponse res,
											HttpSession session) {
		UserRegister signedIn = (UserRegister) session.getAttribute("signin");

		String userid = signedIn.getUserid();

		ProductCart cartInfo = new ProductCart();
		cartInfo.setProduct_no(product_no);
		cartInfo.setUserid(userid);
		cartInfo.setAmount(amount);

		int checkCartInfo = productBO.checkCartInfo(cartInfo);

		if (checkCartInfo > 0) {
			return "exist";
		} else {
			boolean result = productBO.insertCart(cartInfo);
			if (result) {
				return "success";
			} else {
				return "fail";
			}
		}
	}

	@GetMapping("/product/ViewCart")
	public String ViewCartGet(HttpServletRequest req,
							  HttpServletResponse res,
							  HttpSession session,
							  Model model) {
		
		UserRegister userInfo = (UserRegister) session.getAttribute("signin");
		String userid = userInfo.getUserid();
		int totalPrice = 0;
		
		List<CartInfo> cartList = productBO.getUserCart(userid);

		for (CartInfo cartInfo : cartList) {
			totalPrice += cartInfo.getAmount() * cartInfo.getProduct_price();
		}
		model.addAttribute("cartList", cartList);
		model.addAttribute("totalPrice", totalPrice);

		return "/product/ViewCart";
	}

	@PostMapping("/product/deleteCart")
	public @ResponseBody String deleteCartPost(@RequestParam("product_no") int product_no, HttpSession session) {

		UserRegister signedIn = (UserRegister) session.getAttribute("signin");
		String userid = signedIn.getUserid();

		Map<String, Object> delInfo = new HashMap<String, Object>();

		delInfo.put("product_no", product_no);
		delInfo.put("userid", userid);

		boolean result = productBO.deleteCart(delInfo);

		if (result) {
			return "success";
		} else {
			return "failed";
		}

	}

	@GetMapping("/admin/AddProduct")
	public void AddProdGet() {
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String UploadProductPost(@Valid @ModelAttribute ProductRegister productregister,
									MultipartHttpServletRequest mtreq,
									HttpServletRequest req,
									HttpServletResponse res,
									Errors errors) {

		if (errors.hasErrors()) {
			log.info("[관리자메세지]: 입력 값이 누락되었습니다.");
			return "false";
		} else {
			List<Map<String, Object>> productinfo = fileconfig.fileInfo(productregister, mtreq, req);
			boolean results = productBO.registerProduct(productinfo);
			if (results) {
				return "success";
			}else{
				return "fail";
			}
		}
		
	}

	@GetMapping("/admin/ManageAll")
	public String ManageAllGet(Model model) {

		List<ProductRegister> prodInfo = productBO.getAllProductList();
		List<UserRegister> userInfo = userBO.getUserList();

		model.addAttribute("prodInfo", prodInfo);
		model.addAttribute("userInfo", userInfo);

		return "/admin/ManageAll";
	}

	@GetMapping("/admin/updateUser")
	public String UpdateUserGet(String userid, Model model) {
		UserRegister userInfo = userBO.getUser(userid);
		model.addAttribute("userInfo", userInfo);
		return "admin/UpdateUser";
	}

	@PostMapping("/admin/updateUser")
	public String UpdateUserPost(@Valid UpdateUserInfoValidator userInfoNew, Errors errors) {
		boolean results = false;

		if (errors.hasErrors()) {
			log.info("[관리자메세지]: 사용자정보 수정 입력 값이 누락되었습니다.");
			return "redirect:/admin/ManageAll";
		}else {
			results = userBO.updateUser(userInfoNew);	
		}

		if (results) {
			return "redirect:/admin/ManageAll";
		} else {
			log.info("[관리자메세지]: 사용자정보 수정을 실패하였습니다.");
			return "/";
		}

	}

	@GetMapping("/admin/updateProduct")
	public String UpdateProductGet(@RequestParam("product_no") int product_no, Model model) {
		ProductRegister prodInfo = productBO.getProductDetail(product_no);
		model.addAttribute("prodInfo", prodInfo);
		return "/admin/UpdateProduct";
	}

	@PostMapping("/admin/updateProduct")
	public String UpdateProductPost(@Valid UpdateProductInfoValidator prodInfoNew, Errors errors) {
		
		boolean result = false;

		if(errors.hasErrors()) {
			log.info("[관리자메세지]: 상품정보 수정입력 값이 누락되었습니다.");
			return "redirect:/admin/ManageAll";
		}else {
			result = productBO.updateProduct(prodInfoNew);
			if (result) {
				return "redirect:/admin/ManageAll";
			} else {
				log.info("[관리자메세지]: 상품정보 수정을 실패하였습니다.");
				return "/";
			}
		}
		
		
	}

	@PostMapping("/admin/deleteUser")
	public @ResponseBody String deleteUserPost(@RequestParam("userid") String userid) {

		boolean result = userBO.deleteUser(userid);

		if (result) {
			return "success";
		} else {
			return "failed";
		}

	}

	@PostMapping("/admin/deleteProduct")
	public @ResponseBody String deleteProductPost(@RequestParam("product_no") int product_no) {
		boolean result = productBO.deleteProduct(product_no);

		if (result) {
			return "success";
		} else {
			return "failed";
		}

	}
}