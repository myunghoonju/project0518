package com.ddc2.project0518;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ddc2.project0518.model.CartInfo;
import com.ddc2.project0518.model.PageNation;
import com.ddc2.project0518.model.ProductCart;
import com.ddc2.project0518.model.ProductRegister;
import com.ddc2.project0518.model.UpdateProductInfoValidator;
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
	
	public List<ProductRegister> getProductList(PageNation page){
		return productDAO.getProductList(page);
	}
	public List<ProductRegister> getAllProductList(){
		return productDAO.getAllProductList();
	}
	public int count_list(PageNation page) {
		return productDAO.count_list(page);
	}
	public ProductRegister getProductDetail(int productNo) {
		return productDAO.getProductDetail(productNo);
	}
	
	public ArrayList<ProductRegister> prevWatched(int product_no, ProductRegister productDetail,ArrayList<ProductRegister> prevWathedList) {
		 boolean exist = false;
		 if (prevWathedList != null) {
			 	
				if (prevWathedList.size() < 4) {
					for (int i = 0; i < prevWathedList.size(); i++) {
						ProductRegister viewed = prevWathedList.get(i);
						if (product_no == viewed.getProduct_no()) {
							exist = true;
							break;
						}
					}
					if (exist == false) {
						prevWathedList.add(productDetail);
					}

				}

			}else{
				prevWathedList = new ArrayList<ProductRegister>();
				prevWathedList.add(productDetail);
			}

		return prevWathedList;
	}
	
	public int checkCartInfo(ProductCart cartInfo) {
		return productDAO.checkCartInfo(cartInfo);
	}
	
	public boolean insertCart(ProductCart cartInfo) {
		return productDAO.insertCart(cartInfo);
	}
	
	public List<CartInfo> getUserCart(String userid){
		return productDAO.getUserCart(userid);
		
	}
	public boolean deleteCart(Map<String, Object> delInfo) {
		return productDAO.deleteCart(delInfo);
	}
	public boolean updateProduct(UpdateProductInfoValidator prodInfoNew) {
		return productDAO.updateProduct(prodInfoNew);
	}
	public boolean deleteProduct(int product_no) {
		return productDAO.deleteProduct(product_no);
	}
}
