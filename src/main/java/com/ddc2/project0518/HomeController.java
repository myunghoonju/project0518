package com.ddc2.project0518;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ddc2.project0518.model.ProductRegister;
import com.ddc2.project0518.util.FileConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Inject
	FileConfig fileconfig;
	@Inject
	ProductBO productbo;
	
	@GetMapping("/")
	public String UploadProductGet() {
		return "index";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/upload", method=RequestMethod.POST)
	public String UploadProductPost(@Validated@ModelAttribute ProductRegister productregister,MultipartHttpServletRequest mtreq,HttpServletRequest req){
		log.info("prod" + productregister + " ");
		log.info("prod"+ mtreq);
		
		List<Map<String, Object>> productinfo = fileconfig.fileInfo(productregister, mtreq,req);
		
		productbo.register(productinfo);
		
		
		return "index";
	}
	
	

}
