package com.ddc2.project0518;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ddc2.project0518.model.ProductRegister;
import com.ddc2.project0518.security.SecuredUsers;
import com.ddc2.project0518.util.FileConfig;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HomeController {
	
	@Inject
	FileConfig fileconfig;
	@Inject
	ProductBO productbo;
	@Inject
	SecuredUsersBO securedUsersBO; 
	
	@GetMapping("/")
	public ModelAndView SigninGet() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		
		return mv;
	}

	@GetMapping("/signin")
	public ModelAndView testGet2() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("signin");
		return mv;
	}

	@GetMapping("/users/test")
	public ModelAndView testGet() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/test");
		return mv;
	}
	
	
	@PostMapping("/users/auth")
	public ModelAndView SigninPost() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		
		return mv;
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
