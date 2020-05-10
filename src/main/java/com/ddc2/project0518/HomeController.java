package com.ddc2.project0518;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.ddc2.project0518.model.ProductRegister;
import com.ddc2.project0518.model.UserRegister;
import com.ddc2.project0518.util.FileConfig;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Inject
	FileConfig fileconfig;
	@Inject
	ProductBO productbo;
	@Inject
	UserBO userBO;

	
	@GetMapping("/")
	public String StartGet() {
		
		return "index";
	}

	@GetMapping("/users/signin")
	public void SigninGet() {
		
	}@GetMapping("/users/signout")
	public void SignoutGet() {
		
	}
	@GetMapping("/product/AddProduct")
	public void AddProdGet() {
		
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
				System.out.println(signinInfo.getSessionlimit() +"타입스탬프");
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
					//고쳐야됨 
					Date date =new Date(System.currentTimeMillis());
					userBO.keepSignin(signinInfo.getUserid(), session.getId(), date);
				}
			}
		
			return "redirect:/users/signout";
	}
	
	
	
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public ResponseEntity<String> UploadProductPost(@Validated@ModelAttribute ProductRegister productregister,MultipartHttpServletRequest mtreq,HttpServletRequest req,HttpServletResponse res){
		log.info("prod" + productregister + " ");
		
		List<Map<String, Object>> productinfo = fileconfig.fileInfo(productregister, mtreq,req);
		
		boolean results = productbo.registerProduct(productinfo);
		if(results) {
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("failure",HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
