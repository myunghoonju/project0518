package com.ddc2.project0518;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.ddc2.project0518.model.CartInfo;
import com.ddc2.project0518.model.ProductCart;
import com.ddc2.project0518.model.ProductRegister;
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
	
	
	
	@GetMapping(value= {"/","/index"})
	public String StartGet(Model model,HttpSession session) {
		String filepath = session.getServletContext().getRealPath("/");
		List<ProductRegister> ProductList =  productBO.getProductList();
		model.addAttribute("filepath", filepath);
		model.addAttribute("ProductList", ProductList);
		return "index";
	}
	@GetMapping("/users/signin")
	public void SigninGet() {
	}
	@GetMapping("/users/signout")
	public void SignoutGet() {	
	}
	@PostMapping("/signinProcess")
	public String SigninPost(HttpSession session, UserRegister signinInfo, HttpServletResponse res, UserRegister userCookie) {
		String URL = "";
		if(session.getAttribute("signin") != null)
			session.removeAttribute("signin");
		
		UserRegister result = userBO.signin(signinInfo);
		boolean match = pw_encoder.matches(signinInfo.getPassword(), result.getPassword());

		
		if(match) {
			session.setAttribute("signin", result);
			URL = "redirect:/";
			
			if(userCookie.isUserCookie()) {
				Cookie cookie = new Cookie("signinCookie", session.getId()); //sessionid저장
				cookie.setPath("/");
				cookie.setMaxAge(60*10); //10분
				res.addCookie(cookie);
				signinInfo.setSessionlimit(new Timestamp(System.currentTimeMillis() + 1000*(60*10)));
				userBO.keepSignin(signinInfo.getUserid(),session.getId(),signinInfo.getSessionlimit());
			}
			
		}else {
			URL = "redirect:/";
		}
		
		return URL;
	
	}
	
	@PostMapping("/userJoin")
	public @ResponseBody String userJoin(@RequestBody UserRegister userinfo){
		String enpwd = pw_encoder.encode(userinfo.getPassword());
		userinfo.setPassword(enpwd);
		boolean result = userBO.insertUser(userinfo);
		if(result) {
			return "success";
		}else {
			return "failed";
		}
		
	}

	@GetMapping("/signOut")
	public String SignOutGet(HttpSession session, HttpServletRequest req, HttpServletResponse res) {	
			Object obj = session.getAttribute("signin");
			
			if(obj != null) {
				UserRegister signinInfo = (UserRegister)obj;
				session.removeAttribute("signin");
				session.invalidate();
				Cookie signinCookie = WebUtils.getCookie(req, "signinCookie");
				
				if(signinCookie != null) {
					signinCookie.setPath("/");
					signinCookie.setMaxAge(0);
					res.addCookie(signinCookie);
					signinInfo.setSessionlimit(new Timestamp(System.currentTimeMillis()));
					log.info("초기화 시점: " + signinInfo.getSessionlimit());
					userBO.keepSignin(signinInfo.getUserid(), session.getId(), signinInfo.getSessionlimit());
				}//Delete cookie
				
			}//End signout
		
			return "redirect:/";
	}
	
	@GetMapping("/ViewProduct")
	public String ViewProduct(@RequestParam int product_no, HttpServletRequest req, HttpServletResponse res,Model model) {
		log.info("상품번호: "  + product_no);
		HttpSession session = req.getSession();
		
		ProductRegister productDetail =  productBO.getProductDetail(product_no);
		
		if(productDetail != null) {
			model.addAttribute("productDetail", productDetail);
			prevWatched(product_no,productDetail,session);
		}else {
			log.info("존재하지 않는 상품입니다.");
		}
		
		return "/ViewProduct";
	}
	
	private void prevWatched(int product_no,ProductRegister productDetail, HttpSession session) {
		boolean exist = false;
		ArrayList<ProductRegister> prevWathedList;
		prevWathedList = (ArrayList<ProductRegister>)session.getAttribute("prevWatchedList");
		
		if(prevWathedList != null) {
			
			if(prevWathedList.size() < 4) {
				for (int i = 0; i < prevWathedList.size(); i++) {
					ProductRegister viewed = prevWathedList.get(i);
					if(product_no == viewed.getProduct_no()) {		
						exist = true;
						break;
					}
				}
				if(exist == false) {
					prevWathedList.add(productDetail);
				}
			
			}
			
		}else {
			prevWathedList = new ArrayList<ProductRegister>();
			prevWathedList.add(productDetail);
		}
			session.setAttribute("prevWatchedList", prevWathedList);
		
	}
	

	
	@PostMapping("/addCart")
	public @ResponseBody String addCartPost(
								@RequestParam("product_no") int product_no,
								@RequestParam("amount") int amount,
								HttpServletRequest req,
								HttpServletResponse res,
								HttpSession session){
		UserRegister signedIn = (UserRegister)session.getAttribute("signin");
		log.info("번호:" + product_no + "사용자: " + signedIn.getUserid() + "수량: " + amount);
		
		String userid = signedIn.getUserid();
		
		ProductCart cartInfo = new ProductCart();
		cartInfo.setProduct_no(product_no);
		cartInfo.setUserid(userid);
		cartInfo.setAmount(amount);
		
		int checkCartInfo = productBO.checkCartInfo(cartInfo);
		
		if(checkCartInfo > 0) {
			return "exist";
		}else {
			boolean result = productBO.insertCart(cartInfo);
			if(result) {
			return "success";
			}else {
				return "fail";
			}
		}
	}
	
	@GetMapping("/product/ViewCart")
	public String ViewCartGet(HttpServletRequest req, HttpServletResponse res,HttpSession session, Model model) {
		 UserRegister userInfo= (UserRegister)session.getAttribute("signin");
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
	public @ResponseBody String deleteCartPost(@RequestParam("product_no") int product_no,HttpSession session){
		
		UserRegister signedIn = (UserRegister)session.getAttribute("signin");
		String userid = signedIn.getUserid();
		
		Map<String, Object> delInfo = new HashMap<String, Object>();
		
		delInfo.put("product_no", product_no);
		delInfo.put("userid", userid);
		
		boolean result = productBO.deleteCart(delInfo);
		
		if(result) {
			return "success";
		}else {
			return "failed";
		}
		
	}
	
	@GetMapping("/admin/AddProduct")
	public void AddProdGet() {}
	
	@ResponseBody 
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public String UploadProductPost(@Validated@ModelAttribute ProductRegister productregister,MultipartHttpServletRequest mtreq,HttpServletRequest req,HttpServletResponse res){
		List<Map<String, Object>> productinfo = fileconfig.fileInfo(productregister, mtreq,req);
		boolean results = productBO.registerProduct(productinfo);
		if(results) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@GetMapping("/admin/ManageAll")
	public String ManageAllGet(Model model) {
		
		List<ProductRegister> prodInfo =  productBO.getProductList();
		List<UserRegister> userInfo =  userBO.getUserList();
		
		model.addAttribute("prodInfo", prodInfo);
		model.addAttribute("userInfo", userInfo);
		
		
		return "/admin/ManageAll";
	}
	
	@GetMapping("/admin/updateUser")
	public String UpdateUserGet(String userid,Model model) {
			UserRegister userInfo = userBO.getUser(userid);
			model.addAttribute("userInfo", userInfo);
		return "admin/UpdateUser";
	}
	
	@PostMapping("/admin/updateUser")
	public String UpdateUserPost(UserRegister userInfoNew) {
		log.info("수정:" +  userInfoNew.getUserid());
		boolean result =  userBO.updateUser(userInfoNew);
		
		if(result) {
			return "redirect:/admin/ManageAll";			
		}else {
			return "";
		}
		
	}
	
	@GetMapping("/admin/updateProduct")
	public String UpdateProductGet(@RequestParam("product_no") int product_no,Model model) {
			ProductRegister prodInfo = productBO.getProductDetail(product_no) ;
			model.addAttribute("prodInfo", prodInfo);
			return "/admin/UpdateProduct";
	}

}