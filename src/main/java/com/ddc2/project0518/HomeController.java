package com.ddc2.project0518;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

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
	
	@GetMapping("/")
	public String StartGet(Model model,HttpSession session) {
		String filepath = session.getServletContext().getRealPath("/");
		List<ProductRegister> ProductList =  productBO.getProductList();
		model.addAttribute("filepath", filepath);
		model.addAttribute("ProductList", ProductList);
		return "index";
	}
	@GetMapping("/users/signin")
	public void SigninGet() {
	}@GetMapping("/users/signout")
	public void SignoutGet() {	
	}
	
	
	
	
	@PostMapping("/signinProcess")
	public String SigninPost(HttpSession session, UserRegister signinInfo, HttpServletResponse res, UserRegister userCookie) {
		log.info(signinInfo.getUserid() + "의 로그인 시작합니다. 기억하기 여부: " + signinInfo.isUserCookie());
		String URL = "";
		if(session.getAttribute("signin") != null)
			session.removeAttribute("signin");
		
		UserRegister result = userBO.Signin(signinInfo);
		
		if(result != null) {
			session.setAttribute("signin", result);
			URL = "redirect:/";
			
			if(userCookie.isUserCookie()) {
				Cookie cookie = new Cookie("signinCookie", session.getId()); //sessionid저장
				cookie.setPath("/");
				cookie.setMaxAge(60*10); //10분
				res.addCookie(cookie);
				signinInfo.setSessionlimit(new Timestamp(System.currentTimeMillis() + 1000*(60*10)));
				log.info("현재시간 + 10분: " + signinInfo.getSessionlimit());
				userBO.keepSignin(signinInfo.getUserid(),session.getId(),signinInfo.getSessionlimit());
			}
			
		}else {
			URL = "redirect:/";
		}
		
		return URL;
	
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
		
			return "redirect:/users/signout";
	}
	
	
	@GetMapping("/product/AddProduct")
	public void AddProdGet() {
		
	}
	@GetMapping("/product/ViewProduct")
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
		
		return "/product/ViewProduct";
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
	

}