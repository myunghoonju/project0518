package com.ddc2.project0518.mybatis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
}
