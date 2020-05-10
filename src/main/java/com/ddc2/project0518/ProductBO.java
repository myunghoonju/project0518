package com.ddc2.project0518;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ddc2.project0518.mybatis.ProductDAO;

@Service
public class ProductBO {

	@Inject
	ProductDAO productDAO;
	
	public boolean registerProduct(List<Map<String,Object>> productinfo) {
		
		Map<String, Object> prodinfo = null;
		
		for (int info = 0; info < productinfo.size(); info++) {
			prodinfo  = (productinfo.get(info));
		}

		return productDAO.registerProduct(prodinfo);
	}
	
	
}
