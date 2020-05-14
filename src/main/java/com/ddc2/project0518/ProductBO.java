package com.ddc2.project0518;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import com.ddc2.project0518.model.ProductCart;
import com.ddc2.project0518.model.ProductRegister;
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
	
	public List<ProductRegister> getProductList(){
		return productDAO.getProductList();
	}
	public ProductRegister getProductDetail(int productNo) {
		return productDAO.getProductDetail(productNo);
	}
	
	public int checkCartInfo(ProductCart cartInfo) {
		return productDAO.checkCartInfo(cartInfo);
	}
	
	public boolean insertCart(ProductCart cartInfo) {
		return productDAO.insertCart(cartInfo);
	}
	
	public List<ProductCart> getUserCart(String userid){
		return productDAO.getUserCart(userid);
		
	}
	public boolean deleteCart(Map<String, Object> delInfo) {
		return productDAO.deleteCart(delInfo);
	}
}
