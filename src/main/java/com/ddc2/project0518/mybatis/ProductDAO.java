package com.ddc2.project0518.mybatis;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO {

	@Resource(name = "sqlSessionCommon")
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "com.ddc2.project0518.mybatis.Product.";
	
	
	
	public boolean registerProduct(Map<String, Object> prodinfo) {
		return sqlSession.insert(NAMESPACE + "registerProduct", prodinfo) == 1 ? true:false;
	}
}
