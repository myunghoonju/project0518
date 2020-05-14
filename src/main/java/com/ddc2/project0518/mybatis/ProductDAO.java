package com.ddc2.project0518.mybatis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ddc2.project0518.model.CartInfo;
import com.ddc2.project0518.model.ProductCart;
import com.ddc2.project0518.model.ProductRegister;

@Repository
public class ProductDAO {

	@Resource(name = "sqlSessionCommon")
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "com.ddc2.project0518.mybatis.Product.";
	
	
	
	public boolean registerProduct(Map<String, Object> prodinfo) {
		return sqlSession.insert(NAMESPACE + "registerProduct", prodinfo) == 1 ? true:false;
	}
	
	public List<ProductRegister> getProductList(){
		return sqlSession.selectList(NAMESPACE + "getProductList");
	}
	public ProductRegister getProductDetail(int productNo) {
		return sqlSession.selectOne(NAMESPACE + "getProductDetail", productNo);
	}
	public int checkCartInfo(ProductCart cartInfo) {
		return sqlSession.selectOne(NAMESPACE + "checkCartInfo", cartInfo);
	}
	public boolean insertCart(ProductCart cartInfo) {
		return sqlSession.insert(NAMESPACE + "insertCart", cartInfo) == 1 ? true:false;
	}
	public List<CartInfo> getUserCart(String userid){
		return sqlSession.selectList(NAMESPACE + "getUserCart", userid);
	}
	public boolean deleteCart(Map<String, Object> delInfo) {
		return sqlSession.delete(NAMESPACE + "deleteCart", delInfo) == 1 ? true:false;
	}
}
